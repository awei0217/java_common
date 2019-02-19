package tablesaw;


import tech.tablesaw.api.DoubleColumn;

import java.io.IOException;
import java.util.List;

/**
 * @创建人 sunpengwei
 * @创建时间 2019/2/1
 * @描述
 * @联系邮箱
 */
public class TableSawStudy {


    public static void main(String[] args) throws IOException {


        double[] numbers = {1, 2, 3, 4};
        DoubleColumn nc = DoubleColumn.create("Test", numbers);
        System.out.println(nc.print());

        DoubleColumn nc2 = nc.multiply(4);
        System.out.println(nc2.print());


        List<String> list = null;

        for (String s : list){
            System.out.println(s);
        }


    }
}
