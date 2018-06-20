window.downloadDrawing = function (sUrl) {

    debugger;
    //iOS devices do not support downloading. We have to inform user about this.
    if (/(iP)/g.test(navigator.userAgent)) {
        alert('Your device does not support files downloading. Please try again in desktop browser.');
        return false;
    }

    if (window.downloadDrawing.isChrome || window.downloadDrawing.isSafari) {
        var link = document.createElement('a');
        link.href = sUrl;

        if (link.download !== undefined) {
            var fileName = sUrl.substring(sUrl.lastIndexOf('/') + 1, sUrl.length);
            link.download = fileName;
        }

        if (document.createEvent) {
            var e = document.createEvent('MouseEvents');
            e.initEvent('click', true, true);
            link.dispatchEvent(e);
            return; true
        }
    }

    if (sUrl.indexOf('?') === -1) {
        sUrl += '?download';
    }

    window.open(sUrl, '_self');
    return true;
}

window.downloadDrawing.isChrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
window.downloadDrawing.isSafari = navigator.userAgent.toLowerCase().indexOf('safari') > -1;

window.downloadDrawingImg = function(sUrl,fileName){
    var win = $("#downloadFileIframe").window;

    var $a = document.createElement('a');
    $a.setAttribute("href", sUrl);
    $a.setAttribute("download", fileName);

    var evObj = document.createEvent('MouseEvents');
    evObj.initMouseEvent( 'click', true, true, win, 0, 0, 0, 0, 0, false, false, true, false, 0, null);
    $a.dispatchEvent(evObj);

    // var canvas = document.createElement('canvas');
    // var img = document.createElement('img');
    // img.setAttribute("crossOrigin",'Anonymous');
    // img.onload = function(e) {
    //     canvas.width = img.width;
    //     canvas.height = img.height;
    //     var context = canvas.getContext('2d');
    //     context.drawImage(img, 0, 0, img.width, img.height);
    //     var blob = null;
    //     canvas.toBlob(function(blob){});
    //     window.navigator.msSaveBlob(blob,fileName);
    // }
    // img.src = sUrl;

    // var a = $("<a></a>").attr("href", sUrl).attr("download", fileName).appendTo("body");
    // a[0].click();
    // a.remove();

    // var $eleForm = $("<form method='get'></form>");
    // $eleForm.attr("action",sUrl);
    // $(document.body).append($eleForm);
    // $eleForm.submit();



}




