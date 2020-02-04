import React from 'react';
import IndecisionApp from './IndecisionApp';
import Navbar from './Navbar';

export default class Home extends React.Component{
    render(){
   
        return (
            <div>
	            <Navbar/>
				<IndecisionApp/>
           </div>
        );
    }
}