import com.holdenkarau.spark.testing.JavaRDDComparisons;
import com.holdenkarau.spark.testing.SharedJavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.junit.Test;
import scala.reflect.ClassTag;
import utils.SparkUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Tester extends SharedJavaSparkContext implements Serializable {
    private static final long serialVersionUID = -5681683598336701496L;


    @Test
    public void verifyFilterFunction() {
        List<String> input = Arrays.asList(
                "<Posts>",
                "<row Id=\"105081\" PostTypeId=\"2\" ParentId=\"105036\" CreationDate=\"2011-08-31T20:06:28.850\" Score=\"1\" ViewCount=\" Body=\"orm was designed to use MVVM and if\" OwnerUserId=\"22317\" LastActivityDate=\"2011-08-31T20:06:28.850\" />"

        );
        JavaRDD<String> inputRDD = jsc().parallelize(input);
        JavaRDD<String> linesSinPosts =inputRDD.filter(SparkUtil.createFunctionFilter());

        List<String> expectedInput = Arrays.asList(
                "<row Id=\"105081\" PostTypeId=\"2\" ParentId=\"105036\" CreationDate=\"2011-08-31T20:06:28.850\" Score=\"1\" ViewCount=\" Body=\"orm was designed to use MVVM and if\" OwnerUserId=\"22317\" LastActivityDate=\"2011-08-31T20:06:28.850\" />"
        );


        List<String> expectedInput2 = Arrays.asList(
                "<Posts>"
                );
        JavaRDD<String> expectedRDD = jsc()
                .parallelize(expectedInput);

        ClassTag<String> tag =
                scala.reflect.ClassTag$.MODULE$
                        .apply(String.class);

        JavaRDDComparisons.assertRDDEquals(
                JavaRDD.fromRDD(JavaRDD.toRDD(linesSinPosts), tag),
                JavaRDD.fromRDD(JavaRDD.toRDD(expectedRDD), tag));
    }


}
