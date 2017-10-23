package IntermediateCodeGenerator;

public class functionData {
	private int code;
	private int lineOfCode;

	public functionData(int code, int lineOfCode) {
		this.code = code;
		this.lineOfCode = lineOfCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getLineOfCode() {
		return lineOfCode;
	}

	public void setLineOfCode(int lineOfCode) {
		this.lineOfCode = lineOfCode;
	}

	public String toString(){
		return "Function code: "+this.code+", function starts at: "+this.lineOfCode;
	}
}
