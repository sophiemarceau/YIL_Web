

</div>
</div>
<!--Footer-part-->
<div class="row-fluid">
  <div id="footer" class="span12"> 2015 &copy; YoueLink Admin Web. </div>
</div>

<!--end-Footer-part-->

<script src="<%=request.getContextPath()%>/js/excanvas.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/jquery.ui.custom.js"></script> 
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/jquery.flot.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/jquery.flot.resize.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/jquery.peity.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/fullcalendar.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/matrix.js"></script> 
<script src="<%=request.getContextPath()%>/js/jquery.gritter.min.js"></script> 
<!--<script src="js/matrix.interface.js"></script> -->
<script src="<%=request.getContextPath()%>/js/matrix.chat.js"></script> 
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script> 
 
<script src="<%=request.getContextPath()%>/js/jquery.wizard.js"></script> 
<script src="<%=request.getContextPath()%>/js/jquery.uniform.js"></script> 
<script src="<%=request.getContextPath()%>/js/select2.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/matrix.popover.js"></script> 
<script src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/matrix.tables.js"></script> 

<script src="<%=request.getContextPath()%>/js/bootstrap-colorpicker.js"></script> 
<script src="<%=request.getContextPath()%>/js/bootstrap-datepicker.js"></script> 
<script src="<%=request.getContextPath()%>/js/jquery.toggle.buttons.html"></script> 
<script src="<%=request.getContextPath()%>/js/masked.js"></script> 
 
<script src="<%=request.getContextPath()%>/js/matrix.form_validation.js"></script>
<script src="<%=request.getContextPath()%>/js/wysihtml5-0.3.0.js"></script> 
<script src="<%=request.getContextPath()%>/js/jquery.peity.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/bootstrap-wysihtml5.js"></script> 
<!-- <script src="<%=request.getContextPath()%>/js/matrix.form_common.js"></script> -->


<script type="text/javascript">
  // This function is called from the pop-up menus to transfer to
  // a different page. Ignore if the value returned is a null string:
  function goPage (newURL) {

      // if url is empty, skip the menu dividers and reset the menu selection to default
      if (newURL != "") {
      
          // if url is "-", it is this page -- reset the menu:
          if (newURL == "-" ) {
              resetMenu();            
          } 
          // else, send page to designated URL            
          else {  
            document.location.href = newURL;
          }
      }
  }

// resets the menu selection upon entry to this page:
function resetMenu() {
   document.gomenu.selector.selectedIndex = 2;
}
</script>
</body>
</html>
