package Flume.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Flume.Dao.MetricStatDao;
import Flume.Domain.MetricStat;

@Service
public class MetricStatService {
	
	private static final Logger logger = LoggerFactory.getLogger(MetricStatService.class);
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static InputStream is = null;
	private static BufferedReader br = null;
	private static String st = null;
	private static JSONObject jo = null;
	private static JSONArray jsonArr = null;
	HashMap<String,Long> beforeMap=null;
	Long ch_diff=0L;Long snk_diff=0L;
	Long ch_event=0L;Long snk_event=0L;
	
	@Autowired
	MetricStatDao dao;
	
	@Value("${flume.host}")
	String host;
	@Value("${flume.host_name}")
	String host_name;
	@Value("${flume.source_name}")
	String source_name;
	@Value("${flume.channel_name}")
	String channel_name;
	@Value("${flume.sink_name}")
	String sink_name;
	
	
	@Transactional
	public void initMetricStat() {
		
		String shost[]= host.split(",");
		String shost_name[]=host_name.split(",");
		String sflow[]=source_name.split(",");
		String cflow[]=channel_name.split(",");
		String kflow[]=sink_name.split(",");
		jsonArr = new JSONArray();
		MetricStat stat=null;
		for (int i = 0; i < shost.length; i++) {
			
			List<MetricStat> list=new ArrayList<MetricStat>();
			Map<String,Object> map =new HashMap<String, Object>();
			
			try {
				System.out.println("i시작!");
				new URL(shost[i]).openConnection().getContent();
				is = new URL(shost[i]).openStream();
				br = new BufferedReader(new InputStreamReader(is,
						Charset.forName("UTF-8")));
				st = readAll(br);
				jo = new JSONObject(st);
				jsonArr.put(i, jo);
				
				for(int j=1;j<=sflow.length;j++){
					stat=new MetricStat();
					beforeMap=new HashMap<String, Long>();
					beforeMap=dao.selectDiff(shost_name[i],String.valueOf(j));
					if(beforeMap!=null){
						ch_diff=beforeMap.get("ch_diff");
						snk_diff=beforeMap.get("snk_diff");
						ch_event=beforeMap.get("ch_diff");
						snk_event=beforeMap.get("snk_diff");
					}
					System.out.println("j시작!"+j);
					stat.setHost_name(shost_name[i]);
					stat.setFlow_num(String.valueOf(j));
					stat.setStart_time(((JSONObject)jsonArr.get(i)).getJSONObject("SOURCE."+sflow[j-1]).getString("StartTime"));
					stat.setCh_fill_percentage(((JSONObject)jsonArr.get(i)).getJSONObject("CHANNEL."+cflow[j-1]).getString("ChannelFillPercentage"));
					stat.setCh_event(((JSONObject)jsonArr.get(i)).getJSONObject("CHANNEL."+cflow[j-1]).getLong("EventPutSuccessCount"));
					stat.setSnk_event(((JSONObject)jsonArr.get(i)).getJSONObject("SINK."+kflow[j-1]).getLong("EventDrainSuccessCount"));
					stat.setCh_diff(((JSONObject)jsonArr.get(i)).getJSONObject("CHANNEL."+cflow[j-1]).getLong("EventPutSuccessCount")-ch_event);
					stat.setSnk_diff(((JSONObject)jsonArr.get(i)).getJSONObject("SINK."+kflow[j-1]).getLong("EventDrainSuccessCount")-snk_event);
					if(((JSONObject)jsonArr.get(i)).getJSONObject("CHANNEL."+cflow[j-1]).getString("ChannelFillPercentage").equals("0.0")){ 
						stat.setStatus('y');
					}else if(ch_diff/2>= stat.getCh_diff()||
							snk_diff/2>= stat.getSnk_diff()||
							stat.getCh_event()==0||stat.getSnk_event()==0){
								stat.setStatus('e');
					}else{
						stat.setStatus('i');
					}
					list.add(stat);
				}
				map.put("list", list);
				System.out.println(map.get("list"));
				dao.insertStat(map);
				
			} catch (Exception e) {
				e.printStackTrace();
				for(int j=1;j<=sflow.length;j++){
					stat=new MetricStat();
					System.out.println("error j시작!"+j);
					stat.setHost_name(shost_name[i]);
					stat.setFlow_num(String.valueOf(j));
					stat.setStatus('d');  
					map.put("list", list);
					dao.insertStat(map);
				}
			}

		}

	}

}
