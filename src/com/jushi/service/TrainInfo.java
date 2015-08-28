package com.jushi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis.message.MessageElement;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import cn.com.WebXml.GetDetailInfoByTrainCodeResponseGetDetailInfoByTrainCodeResult;
import cn.com.WebXml.GetStationAndTimeByStationNameResponseGetStationAndTimeByStationNameResult;
import cn.com.WebXml.TrainTimeWebServiceLocator;
import cn.com.WebXml.TrainTimeWebServiceSoap;

import com.jushi.pojo.Train;

@SuppressWarnings({ "rawtypes" })
public class TrainInfo {

	// 通过车次得到列车信息
	public ArrayList getInfo(String traincode) {
		ArrayList trainlist = null;
		try {
			java.net.URLEncoder.encode("参数", "UTF-8");
			TrainTimeWebServiceLocator service = new TrainTimeWebServiceLocator();
			TrainTimeWebServiceSoap client = service.getTrainTimeWebServiceSoap();
			GetDetailInfoByTrainCodeResponseGetDetailInfoByTrainCodeResult traininfo = client.getDetailInfoByTrainCode(traincode, "");// 后面的是我新注册的userID:
																																		// eb01f699583d44d4bdee0ed47c0397d5
			MessageElement[] msg = traininfo.get_any();
			List elementBody = msg[1].getChildren();// 消息体信息,DataSet对象
			if (elementBody.size() <= 0) {
				return null;
			}
			String str = elementBody.get(0).toString();// 消息体的字符串形式
			saveXMLString(str, "train.xml");
			trainlist = loadXML("train.xml");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return trainlist;
	}

	public static void saveXMLString(String xmlString, String filename) throws IOException {
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		if (file.canWrite()) {
			FileWriter out = new FileWriter(file);
			out.write(xmlString);
			out.close();
		}
	}

	// 车次查询读取xml文件
	public static ArrayList loadXML(String filepath) throws IOException {
		ArrayList<Train> list = null;
		FileInputStream fis = null;
		try {

			fis = new FileInputStream(filepath);
			list = new ArrayList<Train>();
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(fis);
			Element root = doc.getRootElement();
			List doclist = root.getChildren();
			Element info = null;
			Train train = null;

			for (int i = 0; i < doclist.size(); i++) {
				train = new Train();
				info = (Element) doclist.get(i);
				train.setArriveTime(info.getChild("ArriveTime").getText().equals("http://www.webxml.com.cn") ? "没有数据" : info.getChild("ArriveTime").getText());
				train.setKm(info.getChild("KM").getText().equals("") ? "没有数据" : info.getChild("KM").getText());
				train.setStartTime(info.getChild("StartTime").getText().equals("") ? "没有数据" : info.getChild("StartTime").getText());
				train.setTrainStation(info.getChild("TrainStation").getText().equals("免费用户24小时内访问超过规定数量") ? "没有数据" : info.getChild("TrainStation").getText());

				list.add(train);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 始发站和终点站查询
	public static ArrayList loadXMLByStation(String filepath) throws IOException {
		ArrayList<Train> list = null;
		FileInputStream fis = null;
		try {

			fis = new FileInputStream(filepath);
			list = new ArrayList<Train>();
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(fis);
			Element root = doc.getRootElement();
			List doclist = root.getChildren();
			Element info = null;
			Train train = null;

			for (int i = 0; i < doclist.size(); i++) {
				train = new Train();
				info = (Element) doclist.get(i);
				train.setArriveTime(info.getChild("ArriveTime").getText());
				train.setKm(info.getChild("KM").getText());
				train.setTrainCode(info.getChild("TrainCode").getText());
				train.setFirstStation(info.getChild("FirstStation").getText().equals("数据没有被发现") ? "没有数据" : info.getChild("FirstStation").getText());
				train.setLastStation(info.getChild("LastStation").getText().equals("http://www.webxml.com.cn") ? "没有数据" : info.getChild("LastStation").getText());
				train.setStartStation(info.getChild("StartStation").getText());
				train.setStartTime(info.getChild("StartTime").getText());
				train.setArriveStation(info.getChild("ArriveStation").getText());
				train.setUseDate(info.getChild("UseDate").getText());

				list.add(train);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList getTrainByStation(String startStation, String arriveStation) {
		ArrayList trainlist = null;
		try {
			TrainTimeWebServiceLocator service = new TrainTimeWebServiceLocator();
			TrainTimeWebServiceSoap client = service.getTrainTimeWebServiceSoap();
			GetStationAndTimeByStationNameResponseGetStationAndTimeByStationNameResult traininfo = client.getStationAndTimeByStationName(startStation, arriveStation, "");// userID:
																																											// eb01f699583d44d4bdee0ed47c0397d5
			MessageElement[] msg = traininfo.get_any();
			List elementBody = msg[1].getChildren();// 消息体信息,DataSet对象
			if (elementBody.size() <= 0) {
				return null;
			}
			String str = elementBody.get(0).toString();// 消息体的字符串形式
			saveXMLString(str, "train2.xml");
			trainlist = loadXMLByStation("train2.xml");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return trainlist;
	}

	public static void main(String as[]) {
		// ArrayList list = new TrainInfo().getInfo("K7003");
		// for(int i=0; i<list.size(); i++){
		// Train train = (Train) list.get(i);
		// System.out.println("车站："+train.getTrainStation());
		// System.out.println("开车时间："+train.getStartTime());
		// System.out.println("到站时间："+train.getArriveTime());
		// System.out.println("路程(KM):"+train.getKm());
		// }

		ArrayList list2 = new TrainInfo().getTrainByStation("北京", "哈尔滨");
		for (int i = 0; i < list2.size(); i++) {
			Train train = (Train) list2.get(i);
			System.out.println("车次：" + train.getTrainCode());
			System.out.println("始发站：" + train.getFirstStation());
			System.out.println("终点站：" + train.getLastStation());
			System.out.println("路程(KM):" + train.getKm());
			System.out.println("发车站:" + train.getStartStation());
			System.out.println("发车时间:" + train.getStartTime());
			System.out.println("到达站:" + train.getArriveStation());
			System.out.println("到达时间:" + train.getArriveTime());
			System.out.println("历时:" + train.getUseDate());
		}
	}
}
