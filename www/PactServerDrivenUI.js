/**
 * @exports PactServerDrivenUI
 */
 var PactServerDrivenUI = { };


 /**
  * Initialize SDK with the apiKey and configuration (environment and supportPhoneNumber).
  * 
  * @param {String} apiKey 
  * @param {Object} configuration 
  * @param {Function} callback 
  */
 PactServerDrivenUI.initialize = function (
    apiKey,
    configuration,
    callback
 ) {
    cordova.exec(callback, function(err) {
        callback(err);
    }, "PactServerDrivenUI", "initialize", [apiKey, configuration]);
 };


 /**
  * Show Pact ServerDriven UI.
  * 
  * @param {Object} initialContext 
  * @param {String} landingUri 
  * @param {Function} callback 
  */
 PactServerDrivenUI.show = function(
    initialContext,
    landingUri,
    callback
 ) {
    cordova.exec(callback, function(err) {
        callback(err);
    }, "PactServerDrivenUI", "show", [initialContext, landingUri]);
 };


module.exports = PactServerDrivenUI;