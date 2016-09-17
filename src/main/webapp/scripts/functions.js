$(function() {
	if(!getCookie("EUCookieLaw")){
		$('.banner').css('margin-top','-78px');
	}
});

function setCookie(name,value,days) {
	var date = new Date();
	date.setTime(date.getTime()+(days*24*60*60*1000));
	var expires = "; expires="+date.toGMTString();
	document.cookie = name+"="+value+expires+"; path=/";
};

function getCookie(name) {
	  var nameEQ = name + "=";
	  var ca = document.cookie.split(';');
	  for(var i=0;i < ca.length;i++) {
	    var c = ca[i];
	    while (c.charAt(0)==' ') c = c.substring(1,c.length);
	    if (c.indexOf(nameEQ) == 0) return 'true' === c.substring(nameEQ.length,c.length);
	  }
	  return false;
};

function deleteCookie(name) {
	setCookie(name,"",-1);
};

function createEUCokkieLaw() {
    $('.banner').css('margin-top','0px');
	setCookie("EUCookieLaw",true,365);
};

function eraseEUCokkieLaw() {
	deleteCookie("EUCookieLaw");
};

function toggle(id){
	$(id).toggleClass('modalShow');
};