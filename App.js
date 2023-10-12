/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * 
 */

import React from 'react';
//import { StyleSheet, Text, View } from 'react-native';
import {
   Text,View,StyleSheet,Button,Image,Linking
 } from 'react-native';

 const CleverTap = require('clevertap-react-native');
 //const Stack = createNativeStackNavigator();


 class App extends React.Component {
  render() {
    CleverTap.setDebugLevel(3);    
   CleverTap.addListener(CleverTap.CleverTapPushNotificationClicked, (e)=>{
    CleverTap.recordEvent('Custom_Notification_Clicked_event');
    console.log("e value",e);
  });

  CleverTap.createNotificationChannel("General", "General", "General", 5, true);
  CleverTap.recordEvent('HomePage');

  //Deeplink implimentation start

function _handleOpenUrl(event, from)
{
 console.log('handleOpenUrl', event.url, from);
 if(event.url=='ctdl://ct.com/deep'){
 //  navigation.navigate('Home', { name: 'Jane' })
 console.log('Deeplink found: ',event.url)
 }else{
   console.log('Deeplink failed')
 }
 
}

// Listener to handle incoming deep links
Linking.addEventListener('url', _handleOpenUrl);

/// this handles the case where a deep link launches the application
Linking.getInitialURL().then((url) => {
    if (url) {
        console.log('launch url', url);
        _handleOpenUrl({ url });
    }
    else{
      console.log('No URL found');
    }
}).catch(err => console.error('launch url error', err));

// check to see if CleverTap has a launch deep link
// handles the case where the app is launched from a push notification containing a deep link
CleverTap.getInitialUrl((err, url) => {
    if (url) {
        console.log('CleverTap launch url', url);
        _handleOpenUrl({ url }, 'CleverTap');
    } else if (err) {
        console.log('CleverTap launch url', err);
    }
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
       {/* <Stack.Navigator>
        <Stack.Screen
          name="Home"
          component={HomeScreen}
          options={{title: 'Welcome'}}
        />
        <Stack.Screen name="Profile" component={ProfileScreen} />
      </Stack.Navigator> */}
      
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
//0.71.7


