<template>
  <div>
    <div class="tabs-container grid grid-cols-12 gap-2">
      <div class="col-span-12 h-full lg:col-span-4">
        <user-profile/>
      </div>
      <div class="tabs-content h-full col-span-12 lg:col-span-8">
        <UserInfo/>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">

import UserProfile from "../components/UserProfile.vue";
import UserInfo from "../components/UserInfo.vue";
</script>