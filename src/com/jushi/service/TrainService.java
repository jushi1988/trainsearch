package com.jushi.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings({ "rawtypes" })
public class TrainService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private TrainInfo trainMain = new TrainInfo();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=GBK");
		String traincode = request.getParameter("traincode");
		String firststation = "", laststation = "";
		if (!request.getParameter("firststation").equals("") && !request.getParameter("laststation").equals("")) {
			firststation = request.getParameter("firststation");
			firststation = new String(firststation.getBytes("ISO-8859-1"), "GBK");
			laststation = request.getParameter("laststation");
			laststation = new String(laststation.getBytes("ISO-8859-1"), "GBK");
			ArrayList trainlist = trainMain.getTrainByStation(firststation, laststation);
			request.setAttribute("trainlistStation", trainlist);
		} else {
			ArrayList trainlist = trainMain.getInfo(traincode);
			request.setAttribute("trainlist", trainlist);
		}

		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}
