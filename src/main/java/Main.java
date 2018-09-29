import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineRunner;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Start by defining the options for the pipeline.
        PipelineOptions options = PipelineOptionsFactory.create();

        // Then create the pipeline.
        Pipeline p = Pipeline.create(options);

        List<String> stringList = new ArrayList<>();
        for(int i = 0; i < 1000; i++) stringList.add(i + "");

        PCollection words = p.apply(Create.of(stringList))
        .apply("sysout", ParDo.of(new DoFn<String, String>() {
            @ProcessElement
            public void processElement(@Element String str, OutputReceiver<String> out) {
                System.out.println(str);
                out.output(str);
            }
        }))
        ;

        p.run();
    }
}

class Actual implements Serializable {

    int id;

    Actual(int id) {
        this.id = id;
    }
}
