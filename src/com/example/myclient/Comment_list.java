package com.example.myclient;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clientactive.GetAllStatusMessage;
import clientactive.GetMyCommentMessage;
import clientactive.PostCommentMsg;

import com.example.myclientmodel.CommentMessage;
import com.example.myclientmodel.Location;
import com.example.myclientmodel.ResultInfo;
import com.example.myclientmodel.StatusMessage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Comment_list extends Activity {
    private List<CommentMessage> cmtlist=new ArrayList<CommentMessage>();
    private List<String> nicknamelist=new ArrayList<String>();
    private long msgid=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_list);
		TextView cmtmsg_content=(TextView)findViewById(R.id.textView_msgtext);
		TextView cmtmsg_name=(TextView)findViewById(R.id.textView_comment_msg_name);
		final EditText inputcmt=(EditText)findViewById(R.id.editText_cmt_input);
		Button postcmt=(Button)findViewById(R.id.button_post_comment);
		populatescmtlist();
		populateListView();
		
		Intent nextpage = getIntent();
		
//		postcmt.setOnClickListener(new View.OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				String content=inputcmt.getText().toString();
//				Timestamp ts= new Timestamp(new Date().getTime());
//				PostCommentMsg postit=new PostCommentMsg(0,content,ts,msgid);
//				ResultInfo result=postit.postMsg();
//				//refresh comment
//				GetMyCommentMessage getallmsg=new GetMyCommentMessage(msgid);
//				getallmsg.cmtfromserver();
//				try {
//					cmtlist=getallmsg.jsontocmtlist();
//					nicknamelist=getallmsg.jsontonickname();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println("yes");
//				populateListView();
//			}
//			
//		});
		
//		if (nextpage!=null){
//			
//			try {
//				String msgname=(String) nextpage.getExtras().get("msgname");
//				String msgcontent=(String) nextpage.getExtras().get("msgcontent");
//				try {
//					this.msgid=Integer.parseInt(((String) nextpage.getExtras().get("msgid")));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				cmtmsg_content.setText(msgcontent);
//				cmtmsg_name.setText(msgname);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}

	private void populateListView() {
		ArrayAdapter<CommentMessage> cmtgadapter=new MyListAdapter();
		ListView list= (ListView) findViewById(R.id.listView_cmt_list);
		list.setAdapter(cmtgadapter);//set adapter to listview for viewing
		
	}

	private void populatescmtlist() {
		Timestamp ts= new Timestamp(new Date().getTime());
		cmtlist.add(new CommentMessage(15,"this is mytext1",ts,15));
		cmtlist.add(new CommentMessage(15,"this is mytext2",ts,15));
		cmtlist.add(new CommentMessage(15,"this is mytext3",ts,15));
		cmtlist.add(new CommentMessage(15,"this is mytext4",ts,15));
		nicknamelist.add("PETER1");
		nicknamelist.add("MIKE1");
		nicknamelist.add("JACK1");
		nicknamelist.add("LUCY1");
		
	}

	
	
	private class MyListAdapter extends ArrayAdapter<CommentMessage>{

		public MyListAdapter() {
			super(Comment_list.this, R.layout.cmt_item,cmtlist);//composed by item_view, showing smsglist
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView= convertView;
			if  (itemView==null){
				itemView=getLayoutInflater().inflate(R.layout.cmt_item, parent, false);
			}
			//return super.getView(position, convertView, parent);
			CommentMessage currentcmt= cmtlist.get(position);
			TextView contentview= (TextView) itemView.findViewById(R.id.textView_cmt_content);
			TextView nicknameview=(TextView) itemView.findViewById(R.id.textView1_cmt_nickname);
			TextView timeview=(TextView) itemView.findViewById(R.id.textView_cmt_time);

			
			contentview.setText(currentcmt.getContent());
			nicknameview.setText(nicknamelist.get(position));
			timeview.setText(currentcmt.getDate().toString());

			
			
			return itemView;
			
		}
		

		
	}
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comment_list, menu);
		return true;
	}

}
