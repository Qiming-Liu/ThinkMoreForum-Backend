const { v4: uuidv4 } = require('uuid');
const AWS = require('aws-sdk');

const region = process.env.AWS_REGION;
const UploadBucket = process.env.AWS_UPLOAD_IMAGE_BUCKET;
const accessKeyId = process.env.AWS_ACCESS_KEY_ID
const secretAccessKey = process.env.AWS_SECRET_ACCESS_KEY

AWS.config.update({ region });
const s3 = new AWS.S3({
  region,
  accessKeyId,
  secretAccessKey,
  signatureVersion: 'v4'
});

module.exports.handler = async (event) => {
  console.log('Event: ', event);

  // Random ID for image
  const Key = uuidv4();

  const params = {
    Bucket: UploadBucket,
    Key,
    Expires: 300,
  };

  const uploadURL = await s3.getSignedUrlPromise('putObject', params);

  return {
    statusCode: 200,
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      uploadURL,
      Key,
    }),
  };
};
