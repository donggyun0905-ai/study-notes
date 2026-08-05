package swing;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ColorPaneEx1 extends JTextPane {

  // 단순한 방법으로 색상과 문자열을 추가하는 메서드
  public void appendNaive(Color c, String s) { 
    SimpleAttributeSet aset = new SimpleAttributeSet();
    StyleConstants.setForeground(aset, c); // 텍스트 색상을 설정합니다.

    int len = getText().length();
    setCaretPosition(len); // 캐럿을 끝에 배치합니다 (선택 영역 없음)
    setCharacterAttributes(aset, false); // 지정한 속성을 현재 위치에 적용합니다.
    replaceSelection(s); // 선택된 텍스트가 없으므로 캐럿 위치에 삽입합니다.
  }

  // 고급 방법으로 색상과 문자열을 추가하는 메서드
  public void append(Color c, String s) { 
    StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
        StyleConstants.Foreground, c); // 스타일 속성을 설정합니다.

    int len = getDocument().getLength(); 
    setCaretPosition(len); // 캐럿을 끝에 배치합니다 (선택 영역 없음)
    setCharacterAttributes(aset, false); // 지정한 속성을 현재 위치에 적용합니다.
    replaceSelection(s); // 선택된 텍스트가 없으므로 캐럿 위치에 삽입합니다.
  }

  public static void main(String argv[]) {

    ColorPaneEx1 pane = new ColorPaneEx1();
    // 1부터 400까지의 숫자를 순회하며
    for (int n = 1; n <= 400; n += 1) {
      if (isPrime(n)) { // 소수인지 확인
        pane.append(Color.red, String.valueOf(n) + ' '); // 소수는 빨간색으로 출력
      } else if (isPerfectSquare(n)) { // 완전제곱수인지 확인
        pane.append(Color.blue, String.valueOf(n) + ' '); // 완전제곱수는 파란색으로 출력
      } else {
        pane.append(Color.black, String.valueOf(n) + ' '); // 그 외의 수는 검은색으로 출력
      }
    }

    JFrame f = new JFrame("ColorPane example"); // JFrame을 생성합니다.
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setContentPane(new JScrollPane(pane)); // JScrollPane을 통해 pane을 추가합니다.
    f.setSize(600, 400); // 프레임의 크기를 설정합니다.
    f.setVisible(true); // 프레임을 보이도록 설정합니다.
  }

  // 소수인지 확인하는 메서드
  public static boolean isPrime(int n) {
    if (n < 2) // 2보다 작은 수는 소수가 아님
      return false;
    double max = Math.sqrt(n); // n의 제곱근을 구함
    for (int j = 2; j <= max; j += 1) // 2부터 제곱근까지 반복
      if (n % j == 0) // j가 n의 약수이면
        return false; // 소수가 아님
    return true; // 소수임
  }

  // 완전제곱수인지 확인하는 메서드
  public static boolean isPerfectSquare(int n) {
    int j = 1;
    while (j * j < n && j * j > 0) // j의 제곱이 n보다 작고 오버플로우가 없는 동안 반복
      j += 1;
    return (j * j == n); // j의 제곱이 n과 같으면 완전제곱수임
  }
}
