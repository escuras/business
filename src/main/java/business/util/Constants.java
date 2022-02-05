package business.util;

import business.domain.ActionLog;

public class Constants {

    /**
     * PATHS
     **/
    public static final String BASE_PATH = "/api/v1";

    public static final String DEFAULT_NAME_PROFILE = "USER";
    public static final String DEFAULT_DESCRIPTION_PROFILE = "User by default";

    public static final String PERSON_API_PATH = "/api/person";

    /**
     * RESOURCES
     **/
    public static final String API_ROOT_RESOURCE_PATH = BASE_PATH + "/api";

    /**
     * DATABASE
     **/
    public static final String TABLE_NAME = "SAMPLE";

    /**
     * Table name for entity Person
     */
    public static final String TABLE_PERSON_NAME = "PERSON";

    /**
     * Table name for entity User
     */
    public static final String TABLE_USER_NAME = "USER";

    /**
     * Table name for entity {@link business.domain.Address}
     */
    public static final String TABLE_ADDRESS_NAME = "ADDRESS";

    /**
     * Table name for entity {@link business.domain.Company}
     */
    public static final String TABLE_COMPANY_NAME = "COMPANY";

    /**
     * Table name for entity UserProfile
     */
    public static final String TABLE_USER_PROFILE_NAME = "USER_PROFILE";

    /**
     * Table name for entity {@link ActionLog}
     */
    public static final String TABLE_USER_LOG_NAME = "USER_LOG";

    /**
     * Table name for entity {@link business.domain.Action}
     */
    public static final String TABLE_ACTION_NAME = "ACTION";

    /**
     * Table name for entity Profile
     */
    public static final String TABLE_PROFILE_NAME = "PROFILE";

    /**
     * API DOC
     **/
    public static final String DOC_API = "Api Sample";

    /**
     * PAGINACAO
     **/
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_PAGE_INITAL = "0";
    public static final Integer TOTAL_PAGE_TO_CODE_200 = 1;
}
