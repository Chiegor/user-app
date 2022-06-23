package ru.app.userapp.model;

public class DBConstants {
    // schema name
    public static final String SCHEMA = "users";

    // table name
    public static final String U_TABLE = "userapp.users"; // DBConstants.U_TABLE
    public static final String C_TABLE = "userapp.cities"; // DBConstants.C_TABLE
    public static final String UCL_TABLE = "userapp.users_cities_lived"; // DBConstants.UCL_TABLE
    public static final String UCW_TABLE = "userapp.users_cities_worked"; // DBConstants.UCW_TABLE

    // fields in USER_TABLE
    public static final String USER_ID = "user_id"; // PK, AI, NN
    public static final String USER_NAME = "user_name"; // NN <65>
    // full name USER_TABLE fields
    public static final String U_USER_ID = "users.user_id"; // DBConstants.U_USER_ID
    public static final String U_USER_NAME = "users.user_name"; // DBConstants.U_USER_NAME

    // fields in CITIES_TABLE
    public static final String CITY_ID = "city_id"; // PK, AI, NN
    public static final String CITY_NAME = "city_name"; // NN <65>  DBConstants.CITY_NAME
    // full name CITIES_TABLE fields
    public static final String C_CITY_ID = "cities.city_id"; // DBConstants.C_CITY_ID
    public static final String C_CITY_NAME = "cities.city_name"; // DBConstants.C_CITY_NAME

    // fields in USERS_CITIES_LIVED. Use only for create new schema
    private static final String LIVED_USER_ID = "user_id";
    private static final String LIVED_CITY_ID = "city_id";
    // full name L_TABLE fields
    public static final String UCL_USER_ID = "users_cities_lived.user_id"; // DBConstants.UCL_USER_ID
    public static final String UCL_CITY_ID = "users_cities_lived.city_id"; // DBConstants.UCL_CITY_ID

    // fields in UCW. Use only for create new schema
    private static final String WORKED_USER_ID = "user_id";
    private static final String WORKED_CITY_ID = "city_id";
    // full name W_TABLE fields
    public static final String UCW_USER_ID = "users_cities_worked.user_id";
    public static final String UCW_CITY_ID = "users_cities_worked.city_id";
}

