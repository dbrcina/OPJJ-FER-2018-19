package hr.fer.zemris.java.custom.scripting.nodes;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class NowNode extends Node {

	private String formater;
	private DateTimeFormatter defFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public NowNode(String formater) {
		this.formater = formater;
	}

	public String getFormater() {
		return formater;
	}

	public DateTimeFormatter getDefFormat() {
		return defFormat;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{$");
		sb.append(" NOW " + formater);
		sb.append(" $}");
		return sb.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitNowNode(this);
	}

}
