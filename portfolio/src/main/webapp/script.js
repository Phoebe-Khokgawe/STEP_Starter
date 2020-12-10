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
 
function nextPicture(prefix){
   currentIndex++;
   showImageAndDescription(prefix);
}
 
function prevPicture(prefix){
   if(currentIndex < 0) return;
   currentIndex--;
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
       if(currentIndex >= proDescription.length){return;}
       descriptionContainer.innerText = proDescription[currentIndex];
       imgUrl = 'images/pro-'  + currentIndex + '.jpg';
   } else {
       if(currentIndex >= personalDescription.length){return;}
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
 

