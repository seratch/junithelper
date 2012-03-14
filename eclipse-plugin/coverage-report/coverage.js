var cellShown;
var lineSegmentIdsShown;

function hidePath(cell)
{
   if (lineSegmentIdsShown) {
      setOutlines('none');
      cellShown.style.outlineWidth = 'thin';
      lineSegmentIdsShown = null;

      var sameCell = cell == cellShown;
      cellShown = null;
      return sameCell;
   }

   return false;
}

function setOutlines(outlineStyle)
{
   for (var i = 0; i < lineSegmentIdsShown.length; i++) {
      var item = document.getElementById(lineSegmentIdsShown[i]);
      if (item) item.style.outline = outlineStyle;
   }
}

function showPath(cell, lineSegmentIdsStr)
{
   if (hidePath(cell)) return;

   lineSegmentIdsShown = lineSegmentIdsStr.split(' ');
   setOutlines('thin dashed #0000FF');
   cell.style.outlineWidth = 'medium';
   cellShown = cell;
}

function showHide(callPoints, listIndex)
{
   var tableCell = callPoints.parentNode;

   if (listIndex >= 0) {
      tableCell = tableCell.parentNode;
   }
   else {
      listIndex = 0;
   }

   var list = tableCell.getElementsByTagName('ol')[listIndex].style;
   list.display = list.display == 'none' ? 'block' : 'none';
}

var filesShown = true;
function showHideAllFiles(header)
{
   var table = header.parentNode.parentNode;
   filesShown = !filesShown;
   var newDisplay = filesShown ? 'block' : 'none';
   var rows = table.rows;
   rows[0].cells[1].style.display = newDisplay;

   for (var i = 1; i < rows.length; i++) {
      rows[i].cells[1].style.display = newDisplay;
   }
}

function showHideFiles(files)
{
   var table = files.parentNode.cells[1].getElementsByTagName('table')[0];
   table.style.display = table.style.display == 'none' ? 'block' : 'none';
}

function showHideLines(row)
{
   var cellWithLines = row.cells[2].style;
   cellWithLines.display = cellWithLines.display == 'block' ? 'none' : 'block';
}

var metricCol;
function rowOrder(r1, r2)
{
   var c1 = r1.cells[metricCol];
   var c2 = r2.cells[metricCol];

   if (!c1 || !c2) {
      return -1;
   }

   var t1 = c1.title;
   var t2 = c2.title;

   if (t1 && t2) {
      var s1 = t1.split('/')[1];
      var s2 = t2.split('/')[1];
      return s1 - s2;
   }

   return t1 ? 1 : -1;
}

function sortRows(tbl, metric)
{
   var startRow = 0;
   var endRow = tbl.rows.length;

   if (tbl.rows[0].cells.length == 5) {
      metricCol = 1 + metric;
      startRow = 1;
      endRow--;
   }
   else {
      metricCol = metric;
   }

   var rs = new Array();

   for (var i = startRow; i < endRow; i++) {
      rs[i - startRow] = tbl.rows[i];
   }

   rs.sort(rowOrder);

   for (var i = 0; i < rs.length; i++) {
      rs[i] = rs[i].innerHTML;
   }

   for (var i = 0; i < rs.length; i++) {
      tbl.rows[startRow + i].innerHTML = rs[i];
   }
}

function sortTables(metric)
{
   var tables = document.getElementsByTagName("table");

   for (var i = 0; i < tables.length; i++) {
      sortRows(tables[i], metric);
   }
}