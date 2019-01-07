function nightMode() {
    var oldLink = document.getElementsByTagName("link").item(0);
    var newLink = document.createElement("link");
    newLink.setAttribute("rel", "stylesheet");
    newLink.setAttribute("href", "file:///android_asset/styles_night.css");
    document.getElementsByTagName("head").item(0).replaceChild(newLink, oldLink);
}