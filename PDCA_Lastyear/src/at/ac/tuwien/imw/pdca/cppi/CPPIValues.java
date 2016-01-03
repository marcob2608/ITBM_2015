package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CPPIValues {

    private final static Logger log = LogManager.getLogger(CPPIValues.class);

    private CPPIPlanConfiguration conf;
    private BigDecimal portfolio = null;
    private BigDecimal tsr;
    private boolean roundCalculated = true;
    private BigDecimal floor = null;
    private BigDecimal maturefloor;
    private BigDecimal cushion = null;
    private BigDecimal exposure = null;
    private BigDecimal reserveasset = null;
    private BigDecimal partRiskyAsset = null;
    private BigDecimal partRisklessAsset = null;
    private Integer clock = 0;

    public CPPIValues(CPPIPlanConfiguration conf) {
        super();
        //log.info("Starte Berechnung fuer" + (CPPIService.getInstance().getCurrentPeriod() - 1));
        this.conf = conf;
        this.maturefloor = conf.getPortfolio();
        this.calculateValues();
        this.clock = CPPIService.getInstance().getCurrentPeriod();
        this.portfolio = this.getPortfolioValue();
        this.logEntry();
        CPPIService.getInstance().addCPPIValues(this);
    }

    public String toString() {
        return ("Configuration period: " + getClock() + ", Floor: " + floor.setScale(4, BigDecimal.ROUND_HALF_UP) + ", Cushion: " + cushion.setScale(4, BigDecimal.ROUND_HALF_UP) + ", Exposure: " + exposure.setScale(4, BigDecimal.ROUND_HALF_UP) + ", Reserveasset: " + reserveasset.setScale(4, BigDecimal.ROUND_HALF_UP) + ", PartRisky: " + partRiskyAsset.setScale(4, BigDecimal.ROUND_HALF_UP) + ", PartRiskless: " + partRisklessAsset.setScale(4, BigDecimal.ROUND_HALF_UP) + ", NewPortfolio: " + getPortfolio().setScale(4, BigDecimal.ROUND_HALF_UP));

    }

    private void printStackTrace() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTraceElements.length; i++) {
            log.info(stackTraceElements[i]);
        }
    }

    public CPPIPlanConfiguration getConf() {
        return conf;
    }

    public BigDecimal getPortfolio() {
//        if (this.portfolio == null) {
        this.portfolio = this.getPortfolioValue();
        return this.portfolio;
//        }
//        return this.portfolio;
    }

    public BigDecimal getDirectPortfolioValue() {
        return this.portfolio;
    }

    public void setPortfolio(BigDecimal portfolio) {
        this.portfolio = portfolio;
    }

    public BigDecimal getTsrOld() {
        return CPPIService.getInstance().getCurrentTSR().getValue();
    }

    public BigDecimal getTsr() {
        //       log.info(CPPIService.getInstance().getPreviousStockPrice() + " vs alt: " + CPPIService.getInstance().getPrevpreviousStockPrice());
        return CPPIService.getInstance().getPreviousStockPrice().divide(CPPIService.getInstance().getPrevpreviousStockPrice(), 200, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1));
    }


    public BigDecimal getFloor() {
        return floor;
    }

    public BigDecimal getCushion() {
        return cushion;
    }

    public BigDecimal getExposure() {
        return exposure;
    }

    public BigDecimal getReserveasset() {
        return reserveasset;
    }

    public BigDecimal getRiskAssetValue() {
        return getPortfolio().subtract(reserveasset);
    }

    public BigDecimal getPartRiskyAsset() {
        return partRiskyAsset;
    }

    public BigDecimal getPartRisklessAsset() {
        return partRisklessAsset;
    }

    public BigDecimal getPreviousStockPrice() {
        return CPPIService.getInstance().getPreviousStockPrice();
    }

    public BigDecimal getActualStockPrice() {
        return CPPIService.getInstance().getCurrentStockPrice();
    }


    public void calculateValues() {
        this.calculatePortfolio();
        this.calculateFloor();
        this.calculateCushion();
        this.calculateExposure();
        this.calculateReserveAsset();
        this.calculatePartRiskyAsset();
        this.calculatePartRisklessAsset();
    }

    private void calculatePortfolio() {
        this.portfolio = this.getOldPortfolioValue();
    }

    private void calculateFloor() {
        double p = maturefloor.doubleValue();
        double n = (new BigDecimal(1.0)).add(this.getConf().getRisklessAssetInterest()).doubleValue();
        double e = this.getConf().getRisklessAssetLastDays() - (CPPIService.getInstance().getCurrentPeriod() - 1);
        double div = e / this.getConf().getRisklessAssetLastDays();
        this.floor = new BigDecimal(p / Math.pow(n, div));
    }

    private void calculateCushion() {

        this.cushion = new BigDecimal(0).max(this.getPortfolio().subtract(this.floor));
    }

    private void calculateExposure() {
        BigDecimal laverage = this.conf.getLaverage();
        BigDecimal riskAssetPercent = this.conf.getRiskAssetPercent(); // TODO is it really b
        BigDecimal cushionTimesLaverage = this.cushion.multiply(laverage);
        BigDecimal portfolioTimesRiskAssetPercent = this.getPortfolio().multiply(riskAssetPercent);
        this.exposure = cushionTimesLaverage.min(portfolioTimesRiskAssetPercent);
    }

    private void calculateReserveAsset() {
        this.reserveasset = this.getPortfolio().subtract(this.exposure);
    }

    private void calculatePartRiskyAsset() {
        this.partRiskyAsset = this.exposure.divide(this.getPortfolio(), 100, BigDecimal.ROUND_HALF_UP);
    }

    private void calculatePartRisklessAsset() {
        this.partRisklessAsset = this.reserveasset.divide(this.getPortfolio(), 100, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getMaturefloor() {
        return maturefloor;
    }

    public BigDecimal getOldPortfolioValue() {
        if (CPPIService.getInstance().getCurrentPeriod() - 1 <= 0) {
            return conf.getPortfolio();
        }
        return this.getPreviousValues().getDirectPortfolioValue();
    }

    public CPPIValues getPreviousValues() {
        CPPIValues values = CPPIService.getInstance().getCppiValues(CPPIService.getInstance().getCurrentPeriod() - 1);
        return values;
    }

    public BigDecimal getPortfolioValue() {
        if (CPPIService.getInstance().getCurrentPeriod() - 1 <= 0) {
            return getConf().getPortfolio();
        } else {
            CPPIValues oldPeriod = this.getPreviousValues();
            BigDecimal riskypart = oldPeriod.getExposure().multiply(new BigDecimal(1).add(this.getTsr()));
            double risklessklammer = (new BigDecimal(1).add(getConf().getRisklessAssetInterest())).doubleValue();
            BigDecimal risklesspart = new BigDecimal(Math.pow(risklessklammer, (1 / getConf().getRisklessAssetLastDays())) * oldPeriod.getReserveasset().doubleValue());
            //     log.info("Berechne Portfolio Wert Anhand: Alter Exposure" + oldPeriod.getExposure() + "\n Neuer TSR " + new BigDecimal(1).add(this.getTsr()) + "\n Alter Reserve Asset " + oldPeriod.getReserveasset() + "\n Permanenter Risikoloser Zinsmultiplikator "+(new BigDecimal(1).add(getConf().getRisklessAssetInterest())).doubleValue() + "\n Ergibt: " + riskypart.add(risklesspart));
            return riskypart.add(risklesspart);
        }
    }

    public void logEntry() {
        String s = CPPIService.getInstance().getCurrentPeriod() - 1 + "   " + floor.round(new MathContext(10, RoundingMode.HALF_UP)) + "   " + cushion.round(new MathContext(10, RoundingMode.HALF_UP)) + "   " + getExposure().round(new MathContext(10, RoundingMode.HALF_UP)) + "  " + getReserveasset().round(new MathContext(10, RoundingMode.HALF_UP)) + " " + getPreviousStockPrice().round(new MathContext(10, RoundingMode.HALF_UP)) + " " + getTsr().round(new MathContext(10, RoundingMode.HALF_UP)) + "    " + getPortfolioValue().round(new MathContext(10, RoundingMode.HALF_UP));
        log.info(s);
    }

    public Integer getClock() {
        return clock;
    }

    private void setClock(Integer clock) {
        this.clock = clock;
    }
}
