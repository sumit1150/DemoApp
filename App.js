/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * 
 */

import React from 'react';
//import { StyleSheet, Text, View } from 'react-native';
import {
   Text,View,StyleSheet,Button,Image
 } from 'react-native';

 const CleverTap = require('clevertap-react-native');

 class App extends React.Component {
  render() {
   CleverTap.createNotificationChannel("General", "General", "General", 5, true);
   CleverTap.setDebugLevel(3);
   CleverTap.addListener(CleverTap.CleverTapPushNotificationClicked, (e)=>{
      console.log("Push Callback",e);
   });

     return (
      <View style={{flex:1, backgroundColor:'skyblue'}}>
      <View style={{flex:1,height:190}}>
      <Image style={{width:360,height:180}} source={require('./custom_res/c.png')} />
       <Text style={{textAlign:'center' }}> CleverTap React Native SDK</Text>
       </View>
       <View style={{flex:2.8,backgroundColor:'steelblue', marginTop:10}}>
       <Button  onPress={() => {CleverTap.recordEvent('React_Sumit_Event');}} title="Press to Raise Custom Event"/>
       <Button  onPress={()=>{
         CleverTap.onUserLogin({
         'Name': 'Sumit', 'Identity': '9855290227',
         'Email': 'sumit.kumar@clevertap.com', 'custom1': 123,
         'birthdate': new Date('1992-12-22T06:35:31')
     })}} title="Login"/>
       </View>
       
     </View>
     );
  }
}

const styles = StyleSheet.create({
  container: {
     flex: 1,
     alignItems: 'center',
     justifyContent: 'center',
  },
});

export default App;


