import React from 'react';
import { Client } from '@stomp/stompjs';
var SockJS = require("sockjs-client");
var Stomp = require("stompjs");
import SockJsClient from 'react-stomp';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';

export default class IndecisionApp extends React.Component{
    constructor(props){
        super(props)
        this.state= {
            options: [],
			users: [],
            userMessage:[],
			socket :undefined,
            stompClient:undefined,
            wsurl:'http://localhost:8080/websocket'
			
        }   
		
		  this.reloadUserList = this.reloadUserList.bind(this);
		  this.clickHandler=this.clickHandler.bind(this);
		  this.onConnected=this.onConnected.bind(this);
		  this.onMessageReceived=this.onMessageReceived.bind(this);
		  this.onResultsReceived=this.onResultsReceived.bind(this);
		
		}
	   
	  
	    componentDidMount() {
          this.reloadUserList();
		  //this.state.stompClient.debug = null;
        }
	
        clickHandler(){
         
		  this.client.publish({destination: '/topic/pushNotification'});
        }
  
        componentDidUpdate(){
      
        }
   
       componentWillUnmount(){
          console.log('componentWillUnmount');
	      this.reloadUserList();
       }

 
       reloadUserList(){
         this.state.socket = new SockJS(this.state.wsurl);
         this.state.stompClient = Stomp.over(this.state.socket);
         this.state.stompClient.connect({},this.onConnected);
	   }
	 
       onConnected() {
          this.state.stompClient.subscribe('/topic/pushNotification',this.onMessageReceived);
		 
	    }
   
       onMessageReceived(payload) {
          var message = JSON.parse(payload.body);
              var results= message.messages;
			  var final= results.message1;
			  console.log(results);
			  this.setState({ userMessage:[...this.state.userMessage, ...final]});
	       
      }
	  
   
	  onResultsReceived(payload) {
		  var  results= payload;
		  this.setState({ userMessage: [...this.state.userMessage, ...results]});
      }

   
    
	//2.way
	// <SockJsClient 
          // url = 'http://localhost:8081/websocket'
          // topics={['/topic/message']} 
         // onConnect={console.log("Connection established!")} 
          // onMessage={(res) => this.onResultsReceived(res)}  
          // debug= {true}
          // /> 
		  

    render(){
		
		const style ={
            display: 'flex',
            justifyContent: 'center'
        }
       const { handleMessage } = this.props
        return (
		

           <div className="main">
		   
           <Typography variant="h4" style={style}>User Message Details</Typography>
               <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Id</TableCell>
                            <TableCell>Name</TableCell>
							<TableCell>Updating Time</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.userMessage.map(row => (
                            <TableRow>
                            <TableCell component="th" scope="row">
                             {row.id}
                            </TableCell>
                            <TableCell>{row.name}</TableCell>
							<TableCell>{row.date}</TableCell>
                           </TableRow>
                        ))}
                    </TableBody>
                </Table>

            </div>
        );
    }
}