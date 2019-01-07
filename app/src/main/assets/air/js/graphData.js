      function drawVisualization() {
        var data = google.visualization.arrayToDataTable([
          ['Task', 'Gases que compoem o ar'],
          ['Nitrogênio',   78.1],
          ['Oxigênio',     20.9],
          ['Argônio',       0.9],
          ['Outros',        0.1],
        ]);
        new google.visualization.PieChart(document.getElementById('visualization')).
        draw(data);
      }
      google.setOnLoadCallback(drawVisualization);
