const arr = Array.from(Array(100).keys());

console.log('@arr');
console.log(JSON.stringify(arr,null,2));

let sum = 0;
for (let i = 0; i < 30; i++) {
  sum += arr[i];
  console.log('@sum');
  console.log(sum);
}

console.log('@first sum');
console.log(sum);

sum = 0;
for (let i = 30; i < 60; i++) {
  sum += arr[i];
}

console.log('@first sum');
console.log(sum);

sum = 0;
for (let i = 60; i < arr.length; i++) {
  sum += arr[i];
}

console.log('@first sum');
console.log(sum);
