$(document).ready(function()
{
  //hide the all of the element with class msg_body
  $(".block").hide();
  //toggle the componenet with class msg_body
  $(".collapse").click(function()
  {
    $(this).parent().next(".block").slideToggle(600);
    $(this).text() == "[+]"?$(this).text("[-]"):$(this).text("[+]");
  });
});