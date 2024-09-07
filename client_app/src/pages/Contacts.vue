<template>
  <div>
    <div
      class="liner-container mb-6 mt-5 flex justify-center border-b-2 border-gray"
    >
      <h1
        class="mb-[-2px] inline-block border-b-2 border-[#02346f] pb-3 text-2xl font-bold uppercase"
      >
        Контакты
      </h1>
    </div>
    <InfoSection :info="infoBlock"></InfoSection>
    <div class="grid mt-6 md:grid-cols-2 gap-4">
      <Map></Map>
      <div class="card md:rounded-l w-full bg-base-100 shadow-xl">
        <div class="card-body">
          <h2 class="card-title mb-4 text-2xl">Свяжитесь с нами!</h2>       
          <form class="space-y-4 md:space-y-6" @submit.prevent="sendData()">
            <div>
            <label
              for="name"
              class="block mb-2 text-sm font-medium text-gray-900"
              >Ваше имя</label
            >
            <input
              type="text"
              name="name"
              id="name"
              class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
              placeholder="Иван"
              required=""
            />
          </div>
          <div>
            <label
              for="email"
              class="block mb-2 text-sm font-medium text-gray-900"
              >emai</label
            >
            <input
              type="text"
              name=""
              id="email"
              class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
              placeholder="name@etu.ru"
              required=""
            />
          </div>
          <div>
            <label
              for="message"
              class="block mb-2 text-sm font-medium text-gray-900"
              >Ваше сообщение</label
            >
            <textarea
              type="text"
              name=""
              rows="3"
              id="message"
              class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
              placeholder="..."
              required=""
            ></textarea>
          </div>

          <button
            type="submit"
            class="w-full mt-2 text-white bg-[#02346f] hover:bg-[#bb8d54] focus:ring-4 focus:outline-none transition-all-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
          >
            Отправить
          </button>
        </form>  
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Map from "../components/Map.vue";
import InfoSection from "../components/InfoSection.vue";


let infoBlock = [
  {
    img: 'phone.png',
    title: 'Телефон', 
    description: '+7 (800) 888 00 00'
  }, 
  {
    img: 'mail.png',
    title: 'Почта', 
    description: 'merch@etu.ru'
  }, 
  {
    img: 'hours.png',
    title: 'Часы работы', 
    description: 'с 10:00 до 17:00'
  }, 
]
</script>