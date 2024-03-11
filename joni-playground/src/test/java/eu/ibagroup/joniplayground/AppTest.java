package eu.ibagroup.joniplayground;

import org.joni.Matcher;
import org.joni.Option;
import org.joni.exception.JOniException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.joni.Regex;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private Map<String, String> computeResultStr(String regexStr, String txtToCompute) {
        Map<String, String> result = new HashMap<>();
        result.put("match_i", "none");
        result.put("regions", "none");
        Regex regex;
        try {
            regex = new Regex(regexStr);
        } catch (JOniException e) {
            result.put("match_i", "Result: Failed to parse textmate regex: " + e);
            return result;
        }
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(txtToCompute));
        byte[] txtToComputeBytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(txtToComputeBytes);
        Matcher matcher = regex.matcher(txtToComputeBytes);
        try {
            int matchIndex = matcher.search(0, 0, txtToComputeBytes.length, Option.NONE);
            result.put("match_i", "Result: match index: " + matchIndex);
            if (matchIndex == -1) {
                result.put("match_i", "Result: no match");
            } else {
                String regionsWOShittyFormatting = matcher.getEagerRegion()
                    .toString()
                    .replace("\n", ";")
                    .replaceFirst(";", "");
                result.put("regions", regionsWOShittyFormatting);
            }
        }
        catch (JOniException | ArrayIndexOutOfBoundsException e) {
            result.put("match_i", "Result: Failed to match textmate regex: " + e);
        }
        return result;
    }

    @Test
    @DisplayName("Check Joni simple regex")
    void smoke() {
        String regex = "(?<=,)(\\s)(?<=^.{0}|^.{1}|^.{2}|^.{3}|^.{4}|^.{5}|^.{6}|^.{7}|^.{8}|^.{9}|^.{10}|^.{11}|^.{12}|^.{13}|^.{14}|^.{15}|^.{16}|^.{17}|^.{18}|^.{19}|^.{20}|^.{21}|^.{22}|^.{23}|^.{24}|^.{25}|^.{26}|^.{27}|^.{28}|^.{29}|^.{30}|^.{31}|^.{32}|^.{33}|^.{34}|^.{35}|^.{36}|^.{37}|^.{38}|^.{39}|^.{40}|^.{41}|^.{42}|^.{43}|^.{44}|^.{45}|^.{46}|^.{47}|^.{48}|^.{49}|^.{50}|^.{51}|^.{52}|^.{53}|^.{54}|^.{55}|^.{56}|^.{57}|^.{58}|^.{59}|^.{60}|^.{61}|^.{62}|^.{63}|^.{64}|^.{65}|^.{66}|^.{67}|^.{68}|^.{69}|^.{70}|^.{71})";
        String test = ",alloealalalalala";
        Map<String, String> result = computeResultStr(regex, test);
        System.out.println(result);
    }
}
