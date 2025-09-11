package other;

/**
 * @author dongli
 * @create 2025/7/1 17:36
 * @desc
 */

public enum WarehouseCode {
    WH00000326("451568832329566348"),
    WH00000338("715117975366749876"),
    WH00000333("1118207515831190831"),
    WH00000637("1166585676728651310"),
    WH00000424("3611742529661651423"),
    WH00000436("3966349081739610069"),
    WH00000636("3990922977490258482"),
    WH00000413("4792323415917151222"),
    WH00000414("6013803833611667973"),
    WH00000633("6233013522017108248"),
    WH00000632("6262250736588178457"),
    WH00000634("6520811903395054463"),
    WH00000325("7332136478975019833"),
    WH00000334("7791042832686599737"),
    WH00000337("9110699417676107327");

    private final String code;

    WarehouseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    // 根据编码查找枚举
    public static WarehouseCode fromCode(String code) {
        for (WarehouseCode warehouse : WarehouseCode.values()) {
            if (warehouse.code.equals(code)) {
                return warehouse;
            }
        }
        throw new IllegalArgumentException("未知的仓库编码: " + code);
    }

    // 根据仓库编号查找枚举
    public static WarehouseCode fromWarehouseNumber(String warehouseNumber) {
        for (WarehouseCode warehouse : WarehouseCode.values()) {
            if (warehouse.name().equals(warehouseNumber)) {
                return warehouse;
            }
        }
        throw new IllegalArgumentException("未知的仓库编号: " + warehouseNumber);
    }
}
