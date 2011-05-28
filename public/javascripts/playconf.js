$(document).ready(function()
{
  //hide the all of the element with class msg_body
  $(".blockContent").hide();
  //toggle the componenet with class msg_body
  $(".collapse").click(function()
  {
    $(this).parent().next(".blockContent").slideToggle(400);
    $(this).text() == "[+]"?$(this).text("[-]"):$(this).text("[+]");
  });
});