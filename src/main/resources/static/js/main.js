const URL = "http://localhost:8080";
const showErrorMsg = false;
const errorMsg = "Sorry we coundn't connect to server";

$(".loader-container").hide();

function keyUp(event) {
  let value = $("#searchBox")[0].value;

  if (value === "") $(".suggestions").hide();
  else $(".suggestions").show();

  if (event.key !== "Enter" && value !== "") {
    autocompleteWord(value);
  }

  // if pressed enter
  if (event.key === "Enter" && value !== "") {
    $(".suggestions").hide();
    searchWord(value);
  }
}

function searchThisKey(event) {
  let value = event.target.innerText;
  $("#searchBox")[0].value = value;
  searchWord(value);
  $(".suggestions").hide();
}

function searchWord(value) {
  // call search API
  $(".loader-container").show();
  $(".no-content").hide();
  $(".content").hide();
  axios.get(URL + "/search/" + value).then(response => {
    if (response && response.status === 200) {
      console.log("data: " + response);
      let tag = "";
      response.data.forEach(element => {
        if (element.title !== null && element.description !== null)
          tag +=
            ' <div class="result"><a class="title" href="' +
            element.url +
            '">' +
            element.title +
            '</a><div class="desc">' +
            element.description +
            '</div><div class="link"><a href="' +
            element.url +
            '">' +
            element.url +
            "</a></div></div>";
      });
      $(".content").show();
      $(".content").empty();
      $(".loader-container").hide();
      $(".content").append(tag);
    } else showErrorMsg = true;
  });
}

function autocompleteWord(value) {
  // call autocomplete API
  axios.get(URL + "/autocomplete/" + value).then(response => {
    if (response && response.status === 200) {
      let tag = "";
      response.data.forEach(element => {
        tag +=
          '<div class="suggestion" onclick="searchThisKey(event)">' +
          element +
          "</div>";
      });
      $(".suggestions").empty();
      $(".suggestions").append(tag);
    } else showErrorMsg = true;
  });
}
