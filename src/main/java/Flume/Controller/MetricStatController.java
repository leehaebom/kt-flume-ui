package Flume.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Flume.Dao.MetricStatDao;


@Controller
public class MetricStatController {
	
	@Autowired
	MetricStatDao dao;
	
	private static final Logger logger = LoggerFactory.getLogger(MetricStatController.class);
	
	@RequestMapping(value = "/metric_stat")
	public ModelAndView getMetric() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("StatList", dao.selectList());
		mv.setViewName("./metric_stat");
		return mv;
	}
	
	@RequestMapping(value = "/metric_stat_detail",method = RequestMethod.POST)
	public void getDetail(@RequestParam("host_name") String host_name,@RequestParam("flow_num") String flow_num) {
		System.out.println("host_name : "+host_name+"\n flow_num : "+flow_num);
	}
}
