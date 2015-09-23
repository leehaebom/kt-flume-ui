package Flume.Domain;

import java.sql.Date;

public class MetricStat {
	String host_name;
	String flow_num;
	long ch_diff;
	long snk_diff;
	String check_time;
	String start_time;
	String ch_fill_percentage;
	char status;
	long src_event;
	long ch_event;
	long snk_event;
	
	public String getHost_name() {
		return host_name;
	}
	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}
	public String getFlow_num() {
		return flow_num;
	}
	public void setFlow_num(String flow_num) {
		this.flow_num = flow_num;
	}
	public long getCh_diff() {
		return ch_diff;
	}
	public void setCh_diff(long ch_diff) {
		this.ch_diff = ch_diff;
	}
	public long getSnk_diff() {
		return snk_diff;
	}
	public void setSnk_diff(long snk_diff) {
		this.snk_diff = snk_diff;
	}
	public String getCheck_time() {
		return check_time;
	}
	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
		
	}
	public String getCh_fill_percentage() {
		return ch_fill_percentage;
	}
	public void setCh_fill_percentage(String ch_fill_percentage) {
		this.ch_fill_percentage = ch_fill_percentage;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public long getSrc_event() {
		return src_event;
	}
	public void setSrc_event(long src_event) {
		this.src_event = src_event;
	}
	public long getCh_event() {
		return ch_event;
	}
	public void setCh_event(long ch_event) {
		this.ch_event = ch_event;
	}
	public long getSnk_event() {
		return snk_event;
	}
	public void setSnk_event(long snk_event) {
		this.snk_event = snk_event;
	}
	
	
}

