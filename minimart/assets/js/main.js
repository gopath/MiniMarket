// THE FANCYBOX VIDEO INITALISATION
jQuery(document).ready(function() {
	"use strict";
		jQuery("#style-switcher a.color").click(function() { 
		jQuery("#mainstyle").attr("href",'css/'+jQuery(this).attr('title')+'.css');
		//jQuery('.inverted-bg').addClass(jQuery(this).attr('class'));
		
		var color = jQuery(this).data('color');
		var cnt = 0;
		
		var images = Array();
		
		var types = ['hand', 'icon', 'lev4', 'logo'];
		
		jQuery('.phone-in-hand').attr('src', 'img/colors/' + color + '_' + types[0] + '.png');
		jQuery('.navbar-brand img').attr('src', 'img/colors/' + color + '_' + types[1] + '.png');
		jQuery('.left-col phone img').attr('src', 'img/colors/' + color + '_' + types[2] + '.png');
		jQuery('.header-img').attr('src', 'img/colors/' + color + '_' + types[3] + '.png');

		
		return false;
		});

	jQuery(".style-toggle").click(function(){
		var switcher = jQuery('#style-switcher');
			if (switcher.hasClass('style-active')){
				switcher.animate({marginLeft:'0'}, 300, 'linear');
			} else {
				switcher.animate({marginLeft:'225'}, 300, 'linear');
			}
		switcher.toggleClass('style-active');
		return false;
	});
	
	jQuery(".header-video-link.video").click(function() {
			$.fancybox({
				'padding'		: 0,
				'autoScale'		: false,
				'transitionIn'	: 'none',
				'transitionOut'	: 'none',
				'titleShow'     : false,
				'helpers' : {
					'overlay' : { 'locked' : false }
				},
				'width'			: 640,
				'height'		: 385,
				'href'			: this.href.replace(new RegExp("watch\\?v=", "i"), 'v/') + '&autoplay=1',
				'type'			: 'swf',
				'swf'			: {
				'wmode'				: 'transparent',
				'allowfullscreen'	: 'true'
				}
			});

		return false;
	});

	//FIX APPEAR SCRIPT
	window.scroll(0,1);

	//PRICE ANIMATION
	 $("#pricing .package")
	  .mouseenter(function() {
		$('#pricing .package').removeClass('inverted');
	  $(this).addClass('inverted');
	  })
	  .mouseleave(function() {
		 $('#pricing .package').removeClass('inverted');
	  });

	//PHONE SLIDER
	var slideDefaultOffset = $('.slide-default-offset').css('height').replace('px', '');
	var slideOffset = $('.slide-offset').css('height').replace('px', '');
	var levels = Array();
	var margins = Array();
	var i = 0;

	margins.push('N/A');

		$('.phone-menu .level').each( function() {
			if ($(this).data('level') != 0){
				margins.push(i);   //finding all available screenshot "levels" for further manipulation
			}
			levels.push($(this).data('level'));   //finding all available screenshot "levels" for further manipulation
			i += parseFloat(slideDefaultOffset);
		});
		$(margins).each(function(index, value) {
			var defaultMargin = Math.abs(value) * -1;
			$(".left-col .level" + index).css('margin-top', defaultMargin);
		});
		
	$( ".phone-menu a" ).click(function() {

	  // VISUAL EFFECTS
	  $('.phone-menu .dotted').removeClass('resp-show');
	 
	  if ($(window).width() < 640){
		$(this).parent().find('.text').fadeIn( 200, function() {});
		$('.phone-menu .active .text').fadeOut( 200, function() { });
		
	  } else {
		$('.phone-menu .active .text').slideUp( 200, function() { });
		$(this).parent().find('.text').slideDown( 500, function() {});
	  }
	  
	  $('.phone-menu li').removeClass('active');
	  $(this).parent().find('.dotted').addClass('resp-show');
	  $(this).parent().addClass('active');
	  
		// MARGIN/HEIGHT CALCULATIONS
		var level = $(this).data('level');
		//RESTORE DEFAULT STATE
		$(margins).each(function(index, value) {
			var defaultMargin = Math.abs(value) * -1;
			$(".left-col .level" + index).css('margin-top', defaultMargin);
		});

		$(levels).each(function(index, value) {
			if (index > level) {
				var curMargin = Math.abs(margins[index]) * -1;
				var newMargin = parseFloat(curMargin) - slideOffset;
				$(".left-col .level" + index).css('margin-top', newMargin);
			}
		});
		
		return false;
	});
});

	//INIT LOCAL SCROLL
	jQuery('.navbar').localScroll({hash:true, offset: {top: -100},duration: 800});
				
	//INIT SKROLLR 
	//disable BG parallax for mobile browsers, will be fixed in the upcoming versions
	if (Modernizr.mq("screen and (max-width:1024px)")) {

	} else {

		var s = skrollr.init({
			mobileDeceleration: 1,
			edgeStrategy: 'set',
			forceHeight: false,
			smoothScrolling: true,
			smoothScrollingDuration: 300,
				easing: {
					WTF: Math.random,
					inverted: function(p) {
						return 1-p;
					}
				}
			});	
	}


	//HIGHLIGHT MENU ITEMS ON SCROLL
	jQuery('.appear').appear();
	jQuery('.animate').appear();

	jQuery(".appear").on("appear", function(data) {
			var id = $(this).attr("id");
			jQuery('.nav li').removeClass('active');
			jQuery(".nav a[href='#" + id + "']").parent().addClass("active");					
		});

	jQuery(".animate").on("appear", function(data) {
		
		jQuery(this).addClass("animate_start");
		
	});
