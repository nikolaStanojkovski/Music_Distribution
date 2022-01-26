class AlbumFormUtils {
    static BRONZE = "Bronze";
    static BRONZE_AMOUNT = 10.00;
    static SILVER = "Silver";
    static SILVER_AMOUNT = 20.00;
    static GOLD = "Gold";
    static GOLD_AMOUNT = 50.00;
    static PLATINUM = "Platinum";
    static PLATINUM_AMOUNT = 100.00;
    static DIAMOND = "Diamond";
    static DIAMOND_AMOUNT = 500.00;
    static DEFAULT_AMOUNT = 0.00;

    static getAlbumTier(inputValue) {
        switch (inputValue) {
            case this.BRONZE:
                return this.BRONZE_AMOUNT + " EUR";
            case this.SILVER:
                return this.SILVER_AMOUNT + " EUR";
            case this.GOLD:
                return this.GOLD_AMOUNT + " EUR";
            case this.PLATINUM:
                return this.PLATINUM_AMOUNT + " EUR";
            case this.DIAMOND:
                return this.DIAMOND_AMOUNT + " EUR";
            default:
                return this.DEFAULT_AMOUNT + " EUR";
        }
    }
}
