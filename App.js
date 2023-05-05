/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * 
 */

import React from 'react';
//import { StyleSheet, Text, View } from 'react-native';
import {
   Text,View,StyleSheet
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
        <View style = {styles.container}>
           <Text>Open up App.js to start working on your app!</Text>
           <Text>Changes you make will automatically reload.</Text>
           <Text>Shake your phone to open the developer menu.</Text>
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


