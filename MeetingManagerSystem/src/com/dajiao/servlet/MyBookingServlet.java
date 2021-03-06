package com.dajiao.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dajiao.model.Meeting;
import com.dajiao.model.User;
import com.dajiao.service.MyBookingService;

/**
 * Servlet implementation class MyBookingServlet
 */
@WebServlet("/MyBookingServlet")
public class MyBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyBookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		User user = (User)request.getSession().getAttribute("person");
		String meetingId = (String) request.getParameter("meetingId");
		String modify = null;
		
		
		/*
		User user = (User)request.getSession().getAttribute("person");
		request.setAttribute("bookingList", MyBookingService.getMyBooking(user.getaccount()));
		*/
		
		if (user != null) {
			
			String change = (String)request.getParameter("change");
			if(change != null){
				// info change

				String startdate = (String) request.getParameter("startDate");
				String starttime = (String) request.getParameter("startTime");
				String enddate = (String) request.getParameter("endDate");
				String endtime = (String) request.getParameter("endTime");
				String topic = (String) request.getParameter("topic");
				String meetingRoom = (String) request.getParameter("room");
				String detail = (String) request.getParameter("detail");
				
					Meeting meeting = new Meeting();
					meeting.setId(Integer.parseInt(meetingId));
					meeting.setTopic(topic);
					meeting.setDetail(detail);
					meeting.setRoomname(meetingRoom);
					meeting.setStarttime(Timestamp.valueOf(startdate + " "
							+ starttime));
					meeting.setEndtime(Timestamp.valueOf(enddate + " " + endtime));

					modify = (MyBookingService.modifyMeeting(meeting) == true) ? "1"
							: "2";
			}
			
			String apply = (String)request.getParameter("apply");
			if(apply != null){
				// apply for equipment
				String numA = (String) request.getParameter("equitmentA");
				String numB = (String) request.getParameter("equitmentB");
				String numC = (String) request.getParameter("equitmentC");
				
				if (apply != null && meetingId != null) {
					int a = (numA == null || numA == "") ? 0 : Integer.parseInt(numA);
					int b = (numB == null || numB == "") ? 0 : Integer.parseInt(numB);
					int c = (numC == null || numC == "") ? 0 : Integer.parseInt(numC);

					modify = (MyBookingService.applyEquitment(
							Integer.parseInt(meetingId), a, b, c)) ? "3" : "4";
				}
			}
			
			String submit = (String) request.getParameter("summary");
			
			if(submit != null){
				// submit summary
				String summaryText = (String) request.getParameter("summaryText");
				
				if (submit != null && meetingId != null) {
					modify = (MyBookingService.submitSummary(
							Integer.parseInt(meetingId), summaryText)) ? "5" : "6";
				}
			}
			
//			// test code 
//			List<Meeting> meetingList = new ArrayList<Meeting>();
//			Meeting meeting = new Meeting();
//			meeting.setId(12);
//			meeting.setTopic("topic");
//			meeting.setDetail("hahah");
//			meeting.setRoomname("wobuzhidao");
//			meeting.setStarttime(Timestamp.valueOf("2014-07-12 21:30:00"));
//			meeting.setEndtime(Timestamp.valueOf("2014-07-13 22:00:00"));
//			meetingList.add(meeting);
//			request.setAttribute("bookingList", meetingList);
//			// end of test code
			
			request.setAttribute("bookingList", MyBookingService.getMyBooking(user.getAccount()));
			request.getRequestDispatcher("./myBooking.jsp?modify=" + modify).forward(request, response);
		}else{
			request.getRequestDispatcher("./meetingManager.jsp").forward(request, response);
		}
	}

}
