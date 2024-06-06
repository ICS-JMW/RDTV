package jmw.rdtv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;



@SpringBootApplication
@RestController
public class RdtvApplication {

	public static void main(String[] args) {
		SpringApplication.run(RdtvApplication.class, args);
	}

	//
	//api
	//
//	@RequestMapping(path = "/Upload", method=RequestMethod.POST)	
//	public ModelAndView UploadImages(@RequestBody ){
//		ModelAndView ret = new ModelAndView();
//		ret.setViewName("mainPage.html");
//		return ret;
//	}
	//
	//end of api
	//

	//get function
	@RequestMapping(path = "/", method=RequestMethod.GET)	
	public ModelAndView HomePage(){
		ModelAndView ret = new ModelAndView();
		ret.setViewName("mainPage.html");
		return ret;
	}
	@RequestMapping(path = "/admin", method=RequestMethod.GET)	
	public ModelAndView AdminPage(){
		ModelAndView ret = new ModelAndView();
		ret.setViewName("mainPage.html");
		return ret;
	}
	@RequestMapping(path = "/Event", method=RequestMethod.GET)	
	public ModelAndView EventPage(){
		ModelAndView ret = new ModelAndView();
		ret.setViewName("event.html");
		return ret;
	}
	@RequestMapping(path = "/Upload", method=RequestMethod.GET)	
	public ModelAndView UploadPage(){
		ModelAndView ret = new ModelAndView();
		ret.setViewName("upload.html");
		return ret;
	}
	//to get a json file just return as a list

}
