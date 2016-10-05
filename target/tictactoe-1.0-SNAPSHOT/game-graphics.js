// Constants
var lineColor = "#e0e0e0";
var xColor = "#2020ff";
var oColor = "#ffff20";
var lineWidth = 4;
var numSquares;
var canvasSize;
var squareSize;

function initCanvas(canvas, boardPixels, boardSquares) {
    numSquares = boardSquares;
    canvasSize = boardPixels;
    squareSize = canvasSize / numSquares;
    canvas.width = canvasSize;
    canvas.height = canvasSize;
    return canvas;
}

function createContext(canvas) {
    var context = canvas.getContext('2d');
    context.lineWidth = lineWidth;
    context.fillStyle = "#ffffff";
    return context;
}

function drawO(context, pos) {
    var centerX = (pos.x+0.5) * squareSize;
    var centerY = (pos.y+0.5) * squareSize;
    var radius = squareSize / 2 - lineWidth;
    context.strokeStyle = oColor;
    context.beginPath();
    context.arc(centerX, centerY, radius, 0, 2 * Math.PI);
    context.stroke();
}

function drawX(context, pos) {
    var left = pos.x * squareSize + lineWidth*2;
    var top = pos.y * squareSize + lineWidth*2;
    var right = (pos.x+1) * squareSize - lineWidth*2;
    var bottom = (pos.y+1) * squareSize - lineWidth*2;
    context.strokeStyle = xColor;
    context.beginPath();
    context.moveTo(left, top);
    context.lineTo(right, bottom);
    context.moveTo(left, bottom);
    context.lineTo(right, top);
    context.stroke();
}

function clearPosition(context, pos) {
    context.fillRect(
        pos.x*squareSize + lineWidth/2, 
        pos.y*squareSize + lineWidth/2, 
        squareSize-lineWidth, 
        squareSize-lineWidth
    );
}

function drawBoardGrid(context) {
    context.lineCap = 'round';
    context.strokeStyle = lineColor;
    context.beginPath();

    for (var i = 1;i < numSquares;i++) {  
        context.moveTo(lineWidth, i * squareSize);
        context.lineTo(canvasSize - lineWidth, i * squareSize);
        context.moveTo(i * squareSize, lineWidth);
        context.lineTo(i * squareSize, canvasSize - lineWidth);
    }

    context.stroke();
}

function getBoardPosition (canvas, event) {
    var rect = canvas.getBoundingClientRect();
    return {
        x: Math.trunc((event.clientX - rect.left) / squareSize),
        y: Math.trunc((event.clientY - rect.top) / squareSize)
    };
}

function drawBoardMarks(context, board) {
    for(x=0; x<numSquares; x++) {
        for(y=0; y<numSquares; y++) {
            clearPosition(context, {x : x, y : y});
            var mark = board[y].item[x];
            if(mark === "X") {
                drawX(context, {x : x, y : y});
            } else if(mark === "O") {
                drawO(context, {x : x, y : y});
            }
        }
    }
}