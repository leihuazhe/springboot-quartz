package xyz.common;

/**
 * Constans 常量
 *
 * @author leihz
 * @date 2017/7/6 13:23
 */
public class Constans{
    public static long TASK_COUNT = 1L;

    public static String QUARTZ_TEST = "TEST";
}

        /*public enum Constans {
    QUARTZ_TEST(2,"test"),
    QUARTZ_LOCAL(0,"local"),
    QUARTZ_PROD(1,"prod");

    int status;
    String msg;

    Constans(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static Constans getCosntans(int status){
        for(Constans constans:values()){
            if(status==constans.status){
                return constans;
            }
        }
        return null;
    }
}*/

