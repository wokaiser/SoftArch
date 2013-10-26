/* add callbacks to application controller */
controller.addEventListener('init', content.init);
controller.addEventListener('updatePlayground', content.updatePlayground);
controller.addEventListener('updateStatus', content.updateStatus);

/* start application controller */
controller.run();
