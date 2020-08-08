<template>
  <div class="create">
    <h1>You can add Sample Article</h1>
    <div><input v-model="article.postName" placeholder="投稿者名" /></div>
    <div><input v-model="article.postText"  placeholder="投稿内容" /></div>
    <div><input type="file" @change="onFileChange"><input type="hidden" v-model="article.postImage"/></div>
    <button @click="addArticle">追加</button>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'sample',
  data(){
    return {
      article: {
        postName: '',
        postText: '',
        postImage: ''
      }
    }
  },
  methods: {
    onFileChange(e) {
      const files = e.target.files || e.dataTransfer.files;
      this.article.postImageData = files[0];
      this.article.postImage = files[0].name;
    },
    addArticle(){
      const vm = this
      let formData = new FormData()
      formData.append('postName', this.article.postName);
      formData.append('postText', this.article.postText);
      formData.append('postImage', this.article.postImage);
      formData.append('postImageData', this.article.postImageData);
      const header = {
        headers: {
            'Content-Type': 'multipart/form-data'
        },
      }
      vm.$axios.post('http://ec2-18-181-145-245.ap-northeast-1.compute.amazonaws.com/api/create', formData, header)
      .then(response => {
        console.log(response.data)
        vm.$router.push('/sample')
      })
    }
  }
}
</script>
