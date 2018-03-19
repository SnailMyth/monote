<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Motion Note</title>
    <script type="text/javascript" src="/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="/js/jquery-migrate-1.2.1.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/fileinput.js"></script>
    <script type="text/javascript" src="js/fileinput_locale_zh.js"></script>
    <script type="text/javascript" src="js/theme.js"></script>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" media="all"
          rel="stylesheet" type="text/css"/>
    <link href="/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="/css/bootstrap.min.css" media="all" rel="stylesheet" type="text/css"/>

</head>
<body>
<div class="container">
    <form enctype="multipart/form-data">
        <div class="form-group">
            <div class="file-loading">
                <input id="file" type="file" multiple class="file" data-overwrite-initial="false">
            </div>
        </div>
    </form>
    <script type="text/javascript">
        $("#file").fileinput({
            theme: 'fa',
            uploadUrl: '/test/upload',
            allowedPreviewTypes: false,
            maxFileSize: 1000000,
            maxFilesNum: 10,
            slugCallback: function (filename) {
                return filename.replace('(', '_').replace(']', '_');
            }
        });


    </script>
</div>
</body>
</html>
