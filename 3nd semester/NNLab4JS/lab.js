'use strict';

let canvas = document.querySelector("#canvas");
let canvasSmall = document.querySelector("#canvas-small");
let identifyButton = document.querySelector("#identify");
let output = document.querySelector("#output");

let canvasCtx = canvas.getContext('2d');
let canvasSmallCtx = canvasSmall.getContext('2d');

let results = ["airplane", "automobile", "bird", "cat", "deer", "dog", "frog", "horse", "ship", "truck"];

window.onload = function() {
    let input = document.getElementById('input');
    input.addEventListener('change', handleFiles);
};

function handleFiles(e) {
    output.innerHTML = "";
    let img = new Image;
    img.onload = function() {
        canvasCtx.drawImage(img, 0, 0, 480, 480);
        canvasSmallCtx.drawImage(img, 0, 0, 32, 32);
    };
    img.src = URL.createObjectURL(e.target.files[0]);
}

function identify() {
    let x = convnetjs.img_to_vol(canvasSmall);
    let stats = net.forward(x);
    let answer = stats.w.indexOf(Math.max(...stats.w));
    let result = results[answer];
    output.innerText = "Result: " + result;
    console.log("Answer: " + answer);
    console.log("Stats:");
    console.log(stats);
}

identifyButton.onclick = identify;
