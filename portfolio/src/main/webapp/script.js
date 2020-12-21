// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
 
var currentIndex = -1;
/**
* Adds a random greeting to the page.
*/
function addRandomGreeting() {
 const greetings =
     ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];
 
 // Pick a random greeting.
 const greeting = greetings[Math.floor(Math.random() * greetings.length)];
 
 // Add it to the page.
 const greetingContainer = document.getElementById('greeting-container');
 greetingContainer.innerText = greeting;
}
 
function nextPicture(prefix,numPictures){
   currentIndex = (currentIndex + 1) % numPictures;
   showImageAndDescription(prefix);
}
 
function prevPicture(prefix,numPictures){
   currentIndex = (currentIndex - 1) % numPictures;
   showImageAndDescription(prefix);
}
 
 
function showImageAndDescription(prefix){
   if(currentIndex < 0){return;}
   const proDescription = ['Hi My name is Phoebe Khokgawe. I am 21 years old.',
                           'I went to Princess Chulabhorn College in Thailand.',
                           'I was an exchange student in Japan for a month.',
                           'I am now at Victoria University of Wellington studying Software Engineering. These are my supportive friends I met at university.'];
   const personalDescription = ['Since moving to New Zealand, I have become addicted to skiing!',
                                'I am always keen to try new things!',
                                'I love to explore.',
                                'I am always up for an adventure.',
                                'Taking photos is always a must!',
                                'I can always make time for cocktails.',
                                'I cannot play tennis but I can hold a racket and take photos 😂.'];
   // Add the description to the page.
   const descriptionContainer = document.getElementById('greeting-container');
   var imgURL;
   if(prefix === 'pro'){
       descriptionContainer.innerText = proDescription[currentIndex];
       imgUrl = 'images/pro-'  + currentIndex + '.jpg';
   } else {
       descriptionContainer.innerText = personalDescription[currentIndex];
       imgUrl = 'images/personal-'  + currentIndex + '.jpg';
   }
  
  
   console.log(imgUrl);
   const imgElement = document.createElement('img');
   imgElement.src = imgUrl;
   console.log(imgUrl + '    index');
 
   const imageContainer = document.getElementById('display-image-container');
   // Remove the previous image.
   imageContainer.innerHTML = '';
   imageContainer.appendChild(imgElement);
}
// /** Adds data to the DOM. */
// function addDataToDom(data) {
//   console.log('Adding text to dom: ' + data);

//   const dataContainer = document.getElementById('data-container');
//   dataContainer.innerText = data;
// }

/**
 * Fetch all the comments from the server and add it to DOM.
 */
function loadComments(){
    var maxComments = document.getElementById("quantity").value;
    fetch('/data?quantity=' + maxComments)
        .then(response => response.json())
        .then((comments) => {
            console.log(maxComments + 'Maximum comments');
        const taskListElement = document.getElementById('comments-list');
    //printout the response
        //number of comments to display
        const displayedComments = maxComments == 0? comments.length : maxComments < comments.length ? maxComments : comments.length;
        taskListElement.innerHTML = 'Displaying ' + displayedComments + ' comments'; 

        for (i = 0; i < displayedComments; i++) {
            taskListElement.appendChild(createCommentElement(comments[i]));
        }
  });
}

function createCommentElement(comment){
    const commentElement = document.createElement('li');
    commentElement.innerText = comment;

    console.log('print' + comment);
  
  return commentElement;

}

function deleteAllComments(){
    const request = new Request('/delete-data', {method: 'POST'});
    fetch(request)
    .then((reload) => {
        loadComments();
    });
    console.log('All comments are deleted');

}
getGoogleAPILink();

function createGoogleMap() {
    console.log("going");
    var map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: -33.864933730676675, lng: 151.1961065202642}, zoom: 8});

    map.setMapTypeId('satellite');
      
    const googleSydneyMarker = new google.maps.Marker({
        position: {lat: -33.864933730676675, lng: 151.1961065202642},
        map: map,
        title: 'Google Sydney office'
    });

    const markerDescription = 'Address: Ground floor, 48 Pirrama Rd, Pyrmont NSW 2009, Australia';
    const infoWindow = new google.maps.InfoWindow({
    content: markerDescription,
    });

    infoWindow.open(map,googleSydneyMarker);
}

function getGoogleAPILink(){
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = 'https://maps.googleapis.com/maps/api/js?key=' + keys.mapAPI1;
    document.head.appendChild(script);
}
 

