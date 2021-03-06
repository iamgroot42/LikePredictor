<%@ page import="java.net.URLEncoder"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Like Predictor</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">

    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css" type="text/css">

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="css/animate.min.css" type="text/css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/creative.css" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body id="page-top">

    <nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand page-scroll" href="#page-top">CSE201</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a class="page-scroll" href="#about">About</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#contact">Developers</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <header>
        <div class="header-content">
            <div class="header-content-inner">
                <h1>Facebook Like Predictor</h1>
                <hr>
                <p>A simple Facebook like predictor,made as part of CSE201 course project</p>
                <a href="https://graph.facebook.com/oauth/authorize?client_id=412912138911681&scope=email,user_friends,user_posts&redirect_uri=<%=URLEncoder.encode("http://localhost:8080/Facebook/Home")%>" class="btn btn-primary btn-xl page-scroll">Try it now</a>
            </div>
        </div>
    </header>

    <section class="bg-primary" id="about">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <h2 class="section-heading">About the predictor</h2>
                    <hr class="light">
                    <p class="text-faded" >The app uses machine learning (<a href="https://en.wikipedia.org/wiki/Linear_regression" style=" color: white;"> linear regression</a>) to train on existing posts (features used described in project's <a href="https://github.com/iamgroot42/LikePredictor/blob/master/README.md" style=" color: white;">README</a> on GitHub) on the user's wall.Then, it uses the same posts to predict the number of likes it would most likely get.To predict the users likely to like a post, a frequency histogram is constructed.</p>
                    <a href="https://github.com/iamgroot42/LikePredictor" class="btn btn-default btn-xl">View source code</a>
                </div>
            </div>
        </div>
    </section>

    <section id="contact">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <h2 class="section-heading">Developers</h2>
                    <hr class="primary">
                    <p>This project was made by two CS undergrads (sophomore), studying  at <a href="http://www.iiitd.ac.in/">IIITD</a> as part of our core course project (CSE201).<br>Liked the project? <br>Then star it on GitHub!</p>
                </div>
                <div class="col-lg-4 col-lg-offset-2 text-center">
                    <a href="https://github.com/iamgroot42">
                    <i class="fa fa-github fa-3x wow bounceIn"></i>
                </a>
                    <p>Anshuman Suri</p>
                </div>
                <div class="col-lg-4 text-center">
                    <a href="https://github.com/that-1guy">
                    <i class="fa fa-github fa-3x wow bounceIn" data-wow-delay=".1s"></i>
                </a>
                    <p>Satyam Kumar</p>
                </div>
            </div>
        </div>
    </section>

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="js/jquery.easing.min.js"></script>
    <script src="js/jquery.fittext.js"></script>
    <script src="js/wow.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="js/creative.js"></script>

</body>

</html>
