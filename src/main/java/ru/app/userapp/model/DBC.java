package ru.app.userapp.model;

public class DBC {
    // schema name
    public static final String SCHEMA = "users";

    // table name
    public static final String U_TABLE = "User";
    public static final String C_TABLE = "City";
    public static final String UCL_TABLE = "UCL";
    public static final String UCW_TABLE = "UCW";

    // fields in U_TABLE
    public static final String USER_ID = "userId"; // PK, AI, NN
    public static final String USER_NAME = "userName"; // NN <65>
    // full name U_TABLE fields
    public static final String U_USER_ID = "User.userId";
    public static final String U_USER_NAME = "User.userName";

    // fields in C_TABLE
    public static final String CITY_ID = "cityId"; // PK, AI, NN
    public static final String CITY_NAME = "cityName"; // NN <65>
    // full name C_TABLE fields
    public static final String C_CITY_ID = "City.cityId";
    public static final String C_CITY_NAME = "City.cityName";

    // fields in UCL. Use only for create new schema
    private static final String LIVED_USER_ID = "userId";
    private static final String LIVED_CITY_ID = "cityId";
    // full name L_TABLE fields
    public static final String UCL_USER_ID = "UCL.userId";
    public static final String UCL_CITY_ID = "UCL.cityId";

    // fields in UCW. Use only for create new schema
    private static final String WORKED_USER_ID = "userId";
    private static final String WORKED_CITY_ID = "cityId";
    // full name W_TABLE fields
    public static final String UCW_USER_ID = "UCW.userId";
    public static final String UCW_CITY_ID = "UCW.cityId";
}

