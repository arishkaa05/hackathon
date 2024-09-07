<template>
  <div class="pt-2 transition-all-300 ease-in-out hover:translate-y-[-0.5rem] hover:z-[2]  ">
    <div
      class="card-container  relative flex h-full flex-col overflow-hidden rounded-lg bg-white mb-6 p-5 shadow-md hover:shadow-xl"
    >
      <div class="absolute top-[10px] right-[10px]">
        <div class="p-[2px]">
          <a
            class="tippy tippy-left-wishlist btn-wishlist transition-all-300 flex h-9 w-9 cursor-pointer items-center justify-center gap-2 rounded-lg bg-[rgba(0,0,0,.3)] hover:bg-secondary-content"
            @click="setFavorite()"
          >
            <img class="w-5" src="/public/images/logo/heart.svg" />
          </a>
        </div>
        <div class="p-[2px]">
          <a
            class="tippy tippy-left-card-view btn-open-modal transition-all-300 flex h-9 w-9 cursor-pointer items-center justify-center rounded-lg bg-[rgba(0,0,0,.3)] hover:bg-[#d5a060]"
            href="javascript:void(0)"
            data-target=".quick-view-modal"
          >
            <img class="w-5" src="/public/images/logo/view.png" />
          </a>
        </div>
      </div>
      <div class="h-[190px] rounded-lg bg-[url('/src/assets/products/tshort-blue.jpeg')] bg-contain bg-no-repeat bg-center" > </div> 
      <div class="my-2 flex justify-between">
        <div class="flex">
          <i class="bi bi-star-fill flex text-base text-star"></i>
          <i class="bi bi-star-fill flex text-base text-star"></i>
          <i class="bi bi-star-fill flex text-base text-star"></i>
          <i class="bi bi-star-fill flex text-base text-star"></i>
          <i class="bi bi-star-fill flex text-base text-gray-200"></i>
        </div>
        <div>
          <span
            class="rounded-md bg-green-300 py-1 px-2 text-xs font-bold uppercase text-white"
            >instock</span
          >
        </div>
      </div>
      <div class="my-1">
        <a class="clamp break-all font-medium" href="#">MSI Stealth</a>
      </div>
      <div class="my-1">
        <p class="clamp-2 text-sm text-gray-400">
          {{
            truncatedText(
              "Lorem ipsum dolor sit amet consectetur adipisicing elit. Itaque culpa, odio, qui praesentium dignissimos eaque dolorum porro alias neque, eius animi ipsa voluptates. Optio repellat tempora voluptas, dolores ipsam ad!"
            )
          }}
        </p>
      </div>
      <div class="my-2 flex gap-2">
        <span class="font-bold">Размеры:</span>
        <ul class="flex gap-3">
          <li>S</li>
          <li>M</li>
          <li>L</li>
          <li>XL</li>
        </ul>
      </div>
      <div class="my-2 flex gap-2">
        <div class="block h-3 w-3 rounded-full bg-blue-600"></div>
        <div class="block h-3 w-3 rounded-full bg-red-600"></div>
        <div class="block h-3 w-3 rounded-full bg-yellow-600"></div>
        <div class="block h-3 w-3 rounded-full bg-black"></div>
      </div>
      <div class="my-1">
        <span class="text-lg font-bold">37.00₽</span>
      </div>
      <div class="mt-auto">
        <a
          class="btn-effect transition ease-in-out   bg-[#02346f]   hover:bg-[#bb8d54] duration-300 flex w-full items-center justify-center rounded-lg bg-primary-color p-2"
          href="#"
        >
          <span class="font-bold uppercase text-white">View details</span>
        </a>
      </div>
    </div>
  </div>
</template>

<script setup lang='ts'>
import { useFavoriteStore } from "../stores/favoriteStore";

const favoriteStore = useFavoriteStore();

const setFavorite = () => {
  favoriteStore.setFavoriteProduct('src/assets/products/tshort-blue.jpeg', 'футболка fhgfkh fkhghf cghkkhgchkgcпвч', 1300)
} 

const truncatedText = (text: string) => {
  if (text.length <= 80) {
    return text;
  } else {
    let truncatedString = text.substr(0, 80);
    truncatedString = truncatedString.substr(
      0,
      truncatedString.lastIndexOf(" ")
    );
    return truncatedString + " ...";
  }
};
</script>