import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import org.joni.Regex;
import org.joni.Matcher;
import org.joni.Option;
import org.joni.exception.JOniException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

public class Main extends Application 
{ 
  private String computeResultStr(String regexStr, String txtToCompute) {
    String result = "Result: none at the moment";
    Regex regex;
    try {
      regex = new Regex(regexStr);
    } catch (JOniException e) {
      return "Result: Failed to parse textmate regex: " + e; 
    }
    ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(txtToCompute));
    byte[] txtToComputeBytes = new byte[byteBuffer.remaining()];
    byteBuffer.get(txtToComputeBytes);
    Matcher matcher = regex.matcher(txtToComputeBytes);
    try {
      int matchIndex = matcher.search(0, 0, txtToComputeBytes.length, Option.NONE);
      result = "Result: match index: " + matchIndex;
      if (matchIndex == -1) {
        result = "Result: no match";
      } else {
        result += "\n" + matcher.getEagerRegion();
      }
    }
    catch (JOniException | ArrayIndexOutOfBoundsException e) {
      result = "Result: Failed to match textmate regex: " + e;
    }
    return result;
  }
  
  @Override
  public void start(Stage primaryStage) {
    
    Label regexLabel, checkLabel, resultLabel; 
    TextField regexTxt, checkTxt;
    Button button;
    HBox hbox;
    VBox regexVBox, checkVBox, sceneVBox;
    Scene scene;

    regexLabel = new Label("Type the RegEx:");
    regexTxt = new TextField("");
    regexTxt.setMaxWidth(200);
    
    checkLabel = new Label("Type the text to check:");
    checkTxt = new TextField("");
    checkTxt.setMaxWidth(200);

    resultLabel = new Label("Result: none at the moment");

    regexTxt
      .textProperty()
      .addListener((observable, oldValue, newValue) -> {
        resultLabel.setText(computeResultStr(newValue, checkTxt.getText()));
      });
    
    checkTxt
      .textProperty()
      .addListener((observable, oldValue, newValue) -> {
        resultLabel.setText(computeResultStr(regexTxt.getText(), newValue));
      });

    regexVBox = new VBox(regexLabel, regexTxt);
    regexVBox.setSpacing(5);
    regexVBox.setAlignment(Pos.CENTER_LEFT);
    
    checkVBox = new VBox(checkLabel, checkTxt);
    checkVBox.setSpacing(5);
    checkVBox.setAlignment(Pos.CENTER_LEFT);
    
    hbox = new HBox();
    hbox.getChildren().addAll(regexVBox, checkVBox);
    hbox.setSpacing(10);
    hbox.setAlignment(Pos.CENTER);
    HBox.setMargin(hbox, new Insets(10, 0, 0, 10));

    sceneVBox = new VBox();
    sceneVBox.getChildren().addAll(hbox, resultLabel);
    sceneVBox.setSpacing(20);
    sceneVBox.setAlignment(Pos.CENTER);
    
    scene = new Scene(sceneVBox, 500, 200);
    
    primaryStage.setTitle("jruby/joni regex sandbox");
    primaryStage.setScene(scene);
    primaryStage.show();
  } 
    
  public static void main(String[] args) {

//    launch(args);
  }
} 
