<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-definition>
<!doctype html>

<!-- Paul Irish's technique for targeting IE, modified to only target IE6, applied to the html element instead of body -->
<!--[if lt IE 7 ]><html lang="en" class="no-js ie6"><![endif]-->
<!--[if (gt IE 6)|!(IE)]><!--><html lang="en" class="no-js"><!--<![endif]-->

<head>
	<meta charset="utf-8">
	<title>Easy HTML5 Template</title>
	<meta name="description" content="Easy HTML5 Template">
	<meta name="author" content="">
	<link rel="shortcut icon" href="/favicon.ico">
<% /*
	<!-- apple touch icon 57x57 -->
	<link rel="apple-touch-icon" href="/apple-touch-icon.png">
*/ %>
	<link rel="stylesheet" href="css/screen.css?v=1.0">
	<script src="js/modernizr-1.6.min.js"></script>
<% /*
	<!-- Remove the script reference below if you're using Modernizr -->
	<!--[if lt IE 9]>
	<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
*/ %>
</head>

<!-- If possible, use the body as the container -->
<!-- The "home" class is an example of a dynamic class created on the server for page-specific targeting -->
<body class="home">
<% /* 
	<!-- ******************************************************************** -->
	<!-- The content below is for demonstration of some common HTML5 elements  -->
	<!-- More than likely you'll rip out everything except header/section/footer and start fresh -->
	
	<!-- First header has an ID so you can give it individual styles, and target stuff inside it -->
	<header id="hd1">

		<!-- "hgroup" is used to make two headings into one, to prevent a new document node from forming -->
		<hgroup>
		<h1>Easy HTML5 Template</h1>
		<h2>tagline</h2>
		</hgroup>

		<!-- Main nav, styled by targeting "#hd1 nav"; you can have more than one nav element per page -->
		<nav>
			<ul>
				<li><a href="#">Home</a></li>
				<li><a href="#">About</a></li>
				<li><a href="#">Services</a></li>
				<li><a href="#">Contact</a></li>
			</ul>
		</nav>

	</header><!-- #hd1 -->

	<!-- This is the main "div" that wraps the content generically; don't use "section" for this -->
*/ %>
	<div id="main">

		<!-- The first of two "section" elements for demo purposes; optional class added for styling (hs1 = "home section 1") -->
		<section class="hs1">
		<!-- Each section should begin with a new h1 (not h2), and optionally a header -->
		<!-- You can have more than one header/footer pair on a page -->
			<header>
			<h1>This is a Page Sub Title</h1>
			</header>
	
			<p>Some content...</p>
	
			<!-- The h2 below is a sub heading relative to the h1 in this section, not for the whole document -->
			<h2>Demonstrating EM and STRONG</h2>
			
			<!-- "strong" is used for SEO and contextual hierarchy -->
			<p><strong>This text will have more importance (SEO-wise and contextually)</strong></p>
	
			<!-- "b" is used for stylistic offset of text that's NOT important contextually -->
			<p><b>This text has visual importance but has no contextual or SEO importance</b></p>
			
			<!-- "em" is used for colloquial-style emphasis -->
			<p>This is a <em>very</em> colloquial expression.</p>
			
			<!-- There can be multiple footers on each page -->
			<!-- Secondary headers and footers don't necesarily need ids; they can be targeted via context (i.e. ".hs1 footer") -->
			<footer>
			<!-- incite a riot: http://24ways.org/2009/incite-a-riot -->
			<p>Author: <cite>Louis Lazaris</cite></p>
			</footer>

		</section><!-- .hs1 -->

		<!-- This is another section; doesn't have header/footer because it's not required -->
		<section class="hs2">
		<h1>This is another section</h1>
		<p>This is some dummy content</p>
		</section><!-- .hs2 -->

	</div><!-- #main -->
<% /*
	<!-- The "aside" element could be a sidebar (outside an article or section) -->
	<!-- Or it could reference other tangentially-related content within an article or section -->
	<aside id="sidebar">
	<p>Sidebar content</p>
	</aside>
	
	<!-- The main footer has an ID for targeting, similar to the main header -->
	<footer id="f1">
	<p>copyright &copy; year</p>
	</footer><!-- #f1 -->

<!-- Remote jQuery with local fallback; taken from HTML5 Boilerplate http://html5boilerplate.com -->
<!-- jQuery version might not be the latest; check jquery.com -->
*/ %>	
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
<script>!window.jQuery && document.write(unescape('%3Cscript src="js/jquery-1.4.4.min.js"%3E%3C/script%3E'))</script>
<script src="js/general.js"></script>
<% /*

<!-- asynchronous analytics code by Mathias Bynens; change UA-XXXXX-X to your own code; http://mathiasbynens.be/notes/async-analytics-snippet -->
<!-- this can also be placed in the <head> if you want page views to be tracked quicker -->
<script>
var _gaq = [['_setAccount', 'UA-XXXXX-X'], ['_trackPageview']];
(function(d, t) {
	var g = d.createElement(t),
		s = d.getElementsByTagName(t)[0];
	g.async = true;
	g.src = ('https:' == location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	s.parentNode.insertBefore(g, s);
})(document, 'script');
</script>
*/ %>
</body>
</html>
</s:layout-definition>