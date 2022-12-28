describe("App", () => {
 
    test("should hit login api", async () => {
  const axios = require('axios');
  jest.mock('axios')
   
  const imagedata = [
    {
      "id": 1,
      "username": "ABC",
  }
  ]
   
  axios.get.mockResolvedValue(imagedata)
   
 
  await axios.get("https://kalavithi-service-team-01-dev.herokuapp.com/api/users/login")
   
  expect(axios.get).toHaveBeenCalledTimes(1)
  //expect(data).toEqual(imagedata);
   
    })
    
    test("should get response when api login is hit", async () => {
      const axios = require('axios');
      jest.mock('axios')
     
      const imagedata = [
        {
          "id": 1,
          "username": "ABC",
          
      }
      ]
     
      axios.get.mockResolvedValue(imagedata)
     
      const data = await axios.get("https://kalavithi-service-team-01-dev.herokuapp.com/api/users/login")
     
      expect(axios.get).toHaveBeenCalledTimes(1)
      expect(data).toEqual(imagedata);
     
        })
  });
  
  