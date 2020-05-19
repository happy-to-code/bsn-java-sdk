package chain.tj.model.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 *
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.0.0)",
        comments = "Source: peer.proto")
public class PeerGrpc {

    private PeerGrpc() {
    }

    public static final String SERVICE_NAME = "peer.Peer";

    // Static method descriptors that strictly reflect the proto.
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_NEW_TRANSACTION =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "NewTransaction"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_NEW_QUERY_TRANSACTION =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "NewQueryTransaction"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_BLOCKCHAIN_GET_HEIGHT =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "BlockchainGetHeight"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_BLOCKCHAIN_GET_BLOCK_BY_HASH =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "BlockchainGetBlockByHash"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_BLOCKCHAIN_GET_BLOCK_BY_HEIGHT =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "BlockchainGetBlockByHeight"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_BLOCKCHAIN_GET_TRANSACTION =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "BlockchainGetTransaction"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_BLOCKCHAIN_GET_TRANSACTION_INDEX =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "BlockchainGetTransactionIndex"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_BLOCKCHAIN_GET_TRANSACTION_BLOCK =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "BlockchainGetTransactionBlock"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_GET_MEMBER_LIST =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "GetMemberList"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_GET_PEER_PERMISSIONS =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "GetPeerPermissions"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_SEARCH =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "Search"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_GET_NTX =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "GetNtx"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_GET_PEER_INFO =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "GetPeerInfo"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_GET_PEER_HEALTH_DATA =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "GetPeerHealthData"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_GET_PEER_VERSION =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "GetPeerVersion"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.BalanceAddress,
            chain.tj.model.proto.MyPeer.Balance> METHOD_GET_BALANCE =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "GetBalance"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.BalanceAddress.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.Balance.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.QueryTime,
            chain.tj.model.proto.MyPeer.TotalRes> METHOD_GET_TOTAL =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "GetTotal"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.QueryTime.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.TotalRes.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_SAVE_FIRST_CONN =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "SaveFirstConn"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.TxId,
            chain.tj.model.proto.MyPeer.OnChain> METHOD_IS_ON_CHAIN =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "IsOnChain"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.TxId.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.OnChain.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_LEDGERS =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "Ledgers"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<chain.tj.model.proto.MyPeer.PeerRequest,
            chain.tj.model.proto.MyPeer.PeerResponse> METHOD_GDI =
            io.grpc.MethodDescriptor.create(
                    io.grpc.MethodDescriptor.MethodType.UNARY,
                    generateFullMethodName(
                            "peer.Peer", "GDI"),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerRequest.getDefaultInstance()),
                    io.grpc.protobuf.ProtoUtils.marshaller(chain.tj.model.proto.MyPeer.PeerResponse.getDefaultInstance()));

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static PeerStub newStub(io.grpc.Channel channel) {
        return new PeerStub(channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static PeerBlockingStub newBlockingStub(
            io.grpc.Channel channel) {
        return new PeerBlockingStub(channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
     */
    public static PeerFutureStub newFutureStub(
            io.grpc.Channel channel) {
        return new PeerFutureStub(channel);
    }

    /**
     *
     */
    public static abstract class PeerImplBase implements io.grpc.BindableService {

        /**
         * <pre>
         * PeerRequest: Transaction, PeerResponse:PeerResponse
         * </pre>
         */
        public void newTransaction(chain.tj.model.proto.MyPeer.PeerRequest request,
                                   io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_NEW_TRANSACTION, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: Transaction, PeerResponse:PeerResponse
         * </pre>
         */
        public void newQueryTransaction(chain.tj.model.proto.MyPeer.PeerRequest request,
                                        io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_NEW_QUERY_TRANSACTION, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:BlockchainNumber
         * </pre>
         */
        public void blockchainGetHeight(chain.tj.model.proto.MyPeer.PeerRequest request,
                                        io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_BLOCKCHAIN_GET_HEIGHT, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:Block
         * </pre>
         */
        public void blockchainGetBlockByHash(chain.tj.model.proto.MyPeer.PeerRequest request,
                                             io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_BLOCKCHAIN_GET_BLOCK_BY_HASH, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainNumber, PeerResponse:Block
         * </pre>
         */
        public void blockchainGetBlockByHeight(chain.tj.model.proto.MyPeer.PeerRequest request,
                                               io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_BLOCKCHAIN_GET_BLOCK_BY_HEIGHT, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:Transaction
         * </pre>
         */
        public void blockchainGetTransaction(chain.tj.model.proto.MyPeer.PeerRequest request,
                                             io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_BLOCKCHAIN_GET_TRANSACTION, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:BlockchainNumber
         * </pre>
         */
        public void blockchainGetTransactionIndex(chain.tj.model.proto.MyPeer.PeerRequest request,
                                                  io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_BLOCKCHAIN_GET_TRANSACTION_INDEX, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:BlockchainNumber
         * </pre>
         */
        public void blockchainGetTransactionBlock(chain.tj.model.proto.MyPeer.PeerRequest request,
                                                  io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_BLOCKCHAIN_GET_TRANSACTION_BLOCK, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:MemberList
         * </pre>
         */
        public void getMemberList(chain.tj.model.proto.MyPeer.PeerRequest request,
                                  io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_GET_MEMBER_LIST, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerPermission, PeerResponse:PeerPermissionList
         * </pre>
         */
        public void getPeerPermissions(chain.tj.model.proto.MyPeer.PeerRequest request,
                                       io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_GET_PEER_PERMISSIONS, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: SearchMes, PeerResponse:SearchRes
         * </pre>
         */
        public void search(chain.tj.model.proto.MyPeer.PeerRequest request,
                           io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_SEARCH, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerInfoAddr, PeerResponse:PeerNtx
         * </pre>
         */
        public void getNtx(chain.tj.model.proto.MyPeer.PeerRequest request,
                           io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_GET_NTX, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerInfoAddr, PeerResponse:PeerDetailInfo
         * </pre>
         */
        public void getPeerInfo(chain.tj.model.proto.MyPeer.PeerRequest request,
                                io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_GET_PEER_INFO, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:PeerStatusInfo
         * </pre>
         */
        public void getPeerHealthData(chain.tj.model.proto.MyPeer.PeerRequest request,
                                      io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_GET_PEER_HEALTH_DATA, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:PeerVersion
         * </pre>
         */
        public void getPeerVersion(chain.tj.model.proto.MyPeer.PeerRequest request,
                                   io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_GET_PEER_VERSION, responseObserver);
        }

        /**
         *
         */
        public void getBalance(chain.tj.model.proto.MyPeer.BalanceAddress request,
                               io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.Balance> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_GET_BALANCE, responseObserver);
        }

        /**
         *
         */
        public void getTotal(chain.tj.model.proto.MyPeer.QueryTime request,
                             io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.TotalRes> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_GET_TOTAL, responseObserver);
        }

        /**
         *
         */
        public void saveFirstConn(chain.tj.model.proto.MyPeer.PeerRequest request,
                                  io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_SAVE_FIRST_CONN, responseObserver);
        }

        /**
         *
         */
        public void isOnChain(chain.tj.model.proto.MyPeer.TxId request,
                              io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.OnChain> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_IS_ON_CHAIN, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: subledger, PeerResponse:subledgers
         * </pre>
         */
        public void ledgers(chain.tj.model.proto.MyPeer.PeerRequest request,
                            io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_LEDGERS, responseObserver);
        }

        /**
         * <pre>
         * 通用数据接口 PeerRequest: GeneralReq, PeerResponse:GeneralResp
         * </pre>
         */
        public void gDI(chain.tj.model.proto.MyPeer.PeerRequest request,
                        io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_GDI, responseObserver);
        }

        @java.lang.Override
        public io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            METHOD_NEW_TRANSACTION,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_NEW_TRANSACTION)))
                    .addMethod(
                            METHOD_NEW_QUERY_TRANSACTION,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_NEW_QUERY_TRANSACTION)))
                    .addMethod(
                            METHOD_BLOCKCHAIN_GET_HEIGHT,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_BLOCKCHAIN_GET_HEIGHT)))
                    .addMethod(
                            METHOD_BLOCKCHAIN_GET_BLOCK_BY_HASH,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_BLOCKCHAIN_GET_BLOCK_BY_HASH)))
                    .addMethod(
                            METHOD_BLOCKCHAIN_GET_BLOCK_BY_HEIGHT,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_BLOCKCHAIN_GET_BLOCK_BY_HEIGHT)))
                    .addMethod(
                            METHOD_BLOCKCHAIN_GET_TRANSACTION,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_BLOCKCHAIN_GET_TRANSACTION)))
                    .addMethod(
                            METHOD_BLOCKCHAIN_GET_TRANSACTION_INDEX,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_BLOCKCHAIN_GET_TRANSACTION_INDEX)))
                    .addMethod(
                            METHOD_BLOCKCHAIN_GET_TRANSACTION_BLOCK,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_BLOCKCHAIN_GET_TRANSACTION_BLOCK)))
                    .addMethod(
                            METHOD_GET_MEMBER_LIST,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_GET_MEMBER_LIST)))
                    .addMethod(
                            METHOD_GET_PEER_PERMISSIONS,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_GET_PEER_PERMISSIONS)))
                    .addMethod(
                            METHOD_SEARCH,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_SEARCH)))
                    .addMethod(
                            METHOD_GET_NTX,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_GET_NTX)))
                    .addMethod(
                            METHOD_GET_PEER_INFO,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_GET_PEER_INFO)))
                    .addMethod(
                            METHOD_GET_PEER_HEALTH_DATA,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_GET_PEER_HEALTH_DATA)))
                    .addMethod(
                            METHOD_GET_PEER_VERSION,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_GET_PEER_VERSION)))
                    .addMethod(
                            METHOD_GET_BALANCE,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.BalanceAddress,
                                            chain.tj.model.proto.MyPeer.Balance>(
                                            this, METHODID_GET_BALANCE)))
                    .addMethod(
                            METHOD_GET_TOTAL,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.QueryTime,
                                            chain.tj.model.proto.MyPeer.TotalRes>(
                                            this, METHODID_GET_TOTAL)))
                    .addMethod(
                            METHOD_SAVE_FIRST_CONN,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_SAVE_FIRST_CONN)))
                    .addMethod(
                            METHOD_IS_ON_CHAIN,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.TxId,
                                            chain.tj.model.proto.MyPeer.OnChain>(
                                            this, METHODID_IS_ON_CHAIN)))
                    .addMethod(
                            METHOD_LEDGERS,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_LEDGERS)))
                    .addMethod(
                            METHOD_GDI,
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            chain.tj.model.proto.MyPeer.PeerRequest,
                                            chain.tj.model.proto.MyPeer.PeerResponse>(
                                            this, METHODID_GDI)))
                    .build();
        }
    }

    /**
     *
     */
    public static final class PeerStub extends io.grpc.stub.AbstractStub<PeerStub> {
        private PeerStub(io.grpc.Channel channel) {
            super(channel);
        }

        private PeerStub(io.grpc.Channel channel,
                         io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected PeerStub build(io.grpc.Channel channel,
                                 io.grpc.CallOptions callOptions) {
            return new PeerStub(channel, callOptions);
        }

        /**
         * <pre>
         * PeerRequest: Transaction, PeerResponse:PeerResponse
         * </pre>
         */
        public void newTransaction(chain.tj.model.proto.MyPeer.PeerRequest request,
                                   io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_NEW_TRANSACTION, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: Transaction, PeerResponse:PeerResponse
         * </pre>
         */
        public void newQueryTransaction(chain.tj.model.proto.MyPeer.PeerRequest request,
                                        io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_NEW_QUERY_TRANSACTION, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:BlockchainNumber
         * </pre>
         */
        public void blockchainGetHeight(chain.tj.model.proto.MyPeer.PeerRequest request,
                                        io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_HEIGHT, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:Block
         * </pre>
         */
        public void blockchainGetBlockByHash(chain.tj.model.proto.MyPeer.PeerRequest request,
                                             io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_BLOCK_BY_HASH, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainNumber, PeerResponse:Block
         * </pre>
         */
        public void blockchainGetBlockByHeight(chain.tj.model.proto.MyPeer.PeerRequest request,
                                               io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_BLOCK_BY_HEIGHT, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:Transaction
         * </pre>
         */
        public void blockchainGetTransaction(chain.tj.model.proto.MyPeer.PeerRequest request,
                                             io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_TRANSACTION, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:BlockchainNumber
         * </pre>
         */
        public void blockchainGetTransactionIndex(chain.tj.model.proto.MyPeer.PeerRequest request,
                                                  io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_TRANSACTION_INDEX, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:BlockchainNumber
         * </pre>
         */
        public void blockchainGetTransactionBlock(chain.tj.model.proto.MyPeer.PeerRequest request,
                                                  io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_TRANSACTION_BLOCK, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:MemberList
         * </pre>
         */
        public void getMemberList(chain.tj.model.proto.MyPeer.PeerRequest request,
                                  io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_GET_MEMBER_LIST, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerPermission, PeerResponse:PeerPermissionList
         * </pre>
         */
        public void getPeerPermissions(chain.tj.model.proto.MyPeer.PeerRequest request,
                                       io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_GET_PEER_PERMISSIONS, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: SearchMes, PeerResponse:SearchRes
         * </pre>
         */
        public void search(chain.tj.model.proto.MyPeer.PeerRequest request,
                           io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_SEARCH, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerInfoAddr, PeerResponse:PeerNtx
         * </pre>
         */
        public void getNtx(chain.tj.model.proto.MyPeer.PeerRequest request,
                           io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_GET_NTX, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerInfoAddr, PeerResponse:PeerDetailInfo
         * </pre>
         */
        public void getPeerInfo(chain.tj.model.proto.MyPeer.PeerRequest request,
                                io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_GET_PEER_INFO, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:PeerStatusInfo
         * </pre>
         */
        public void getPeerHealthData(chain.tj.model.proto.MyPeer.PeerRequest request,
                                      io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_GET_PEER_HEALTH_DATA, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:PeerVersion
         * </pre>
         */
        public void getPeerVersion(chain.tj.model.proto.MyPeer.PeerRequest request,
                                   io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_GET_PEER_VERSION, getCallOptions()), request, responseObserver);
        }

        /**
         *
         */
        public void getBalance(chain.tj.model.proto.MyPeer.BalanceAddress request,
                               io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.Balance> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_GET_BALANCE, getCallOptions()), request, responseObserver);
        }

        /**
         *
         */
        public void getTotal(chain.tj.model.proto.MyPeer.QueryTime request,
                             io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.TotalRes> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_GET_TOTAL, getCallOptions()), request, responseObserver);
        }

        /**
         *
         */
        public void saveFirstConn(chain.tj.model.proto.MyPeer.PeerRequest request,
                                  io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_SAVE_FIRST_CONN, getCallOptions()), request, responseObserver);
        }

        /**
         *
         */
        public void isOnChain(chain.tj.model.proto.MyPeer.TxId request,
                              io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.OnChain> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_IS_ON_CHAIN, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * PeerRequest: subledger, PeerResponse:subledgers
         * </pre>
         */
        public void ledgers(chain.tj.model.proto.MyPeer.PeerRequest request,
                            io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_LEDGERS, getCallOptions()), request, responseObserver);
        }

        /**
         * <pre>
         * 通用数据接口 PeerRequest: GeneralReq, PeerResponse:GeneralResp
         * </pre>
         */
        public void gDI(chain.tj.model.proto.MyPeer.PeerRequest request,
                        io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(METHOD_GDI, getCallOptions()), request, responseObserver);
        }
    }

    /**
     *
     */
    public static final class PeerBlockingStub extends io.grpc.stub.AbstractStub<PeerBlockingStub> {
        private PeerBlockingStub(io.grpc.Channel channel) {
            super(channel);
        }

        private PeerBlockingStub(io.grpc.Channel channel,
                                 io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected PeerBlockingStub build(io.grpc.Channel channel,
                                         io.grpc.CallOptions callOptions) {
            return new PeerBlockingStub(channel, callOptions);
        }

        /**
         * <pre>
         * PeerRequest: Transaction, PeerResponse:PeerResponse
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse newTransaction(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_NEW_TRANSACTION, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: Transaction, PeerResponse:PeerResponse
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse newQueryTransaction(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_NEW_QUERY_TRANSACTION, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:BlockchainNumber
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse blockchainGetHeight(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_BLOCKCHAIN_GET_HEIGHT, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:Block
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse blockchainGetBlockByHash(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_BLOCKCHAIN_GET_BLOCK_BY_HASH, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainNumber, PeerResponse:Block
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse blockchainGetBlockByHeight(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_BLOCKCHAIN_GET_BLOCK_BY_HEIGHT, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:Transaction
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse blockchainGetTransaction(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_BLOCKCHAIN_GET_TRANSACTION, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:BlockchainNumber
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse blockchainGetTransactionIndex(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_BLOCKCHAIN_GET_TRANSACTION_INDEX, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:BlockchainNumber
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse blockchainGetTransactionBlock(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_BLOCKCHAIN_GET_TRANSACTION_BLOCK, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:MemberList
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse getMemberList(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_GET_MEMBER_LIST, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerPermission, PeerResponse:PeerPermissionList
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse getPeerPermissions(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_GET_PEER_PERMISSIONS, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: SearchMes, PeerResponse:SearchRes
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse search(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_SEARCH, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerInfoAddr, PeerResponse:PeerNtx
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse getNtx(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_GET_NTX, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerInfoAddr, PeerResponse:PeerDetailInfo
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse getPeerInfo(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_GET_PEER_INFO, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:PeerStatusInfo
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse getPeerHealthData(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_GET_PEER_HEALTH_DATA, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:PeerVersion
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse getPeerVersion(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_GET_PEER_VERSION, getCallOptions(), request);
        }

        /**
         *
         */
        public chain.tj.model.proto.MyPeer.Balance getBalance(chain.tj.model.proto.MyPeer.BalanceAddress request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_GET_BALANCE, getCallOptions(), request);
        }

        /**
         *
         */
        public chain.tj.model.proto.MyPeer.TotalRes getTotal(chain.tj.model.proto.MyPeer.QueryTime request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_GET_TOTAL, getCallOptions(), request);
        }

        /**
         *
         */
        public chain.tj.model.proto.MyPeer.PeerResponse saveFirstConn(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_SAVE_FIRST_CONN, getCallOptions(), request);
        }

        /**
         *
         */
        public chain.tj.model.proto.MyPeer.OnChain isOnChain(chain.tj.model.proto.MyPeer.TxId request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_IS_ON_CHAIN, getCallOptions(), request);
        }

        /**
         * <pre>
         * PeerRequest: subledger, PeerResponse:subledgers
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse ledgers(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_LEDGERS, getCallOptions(), request);
        }

        /**
         * <pre>
         * 通用数据接口 PeerRequest: GeneralReq, PeerResponse:GeneralResp
         * </pre>
         */
        public chain.tj.model.proto.MyPeer.PeerResponse gDI(chain.tj.model.proto.MyPeer.PeerRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_GDI, getCallOptions(), request);
        }
    }

    /**
     *
     */
    public static final class PeerFutureStub extends io.grpc.stub.AbstractStub<PeerFutureStub> {
        private PeerFutureStub(io.grpc.Channel channel) {
            super(channel);
        }

        private PeerFutureStub(io.grpc.Channel channel,
                               io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected PeerFutureStub build(io.grpc.Channel channel,
                                       io.grpc.CallOptions callOptions) {
            return new PeerFutureStub(channel, callOptions);
        }

        /**
         * <pre>
         * PeerRequest: Transaction, PeerResponse:PeerResponse
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> newTransaction(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_NEW_TRANSACTION, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: Transaction, PeerResponse:PeerResponse
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> newQueryTransaction(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_NEW_QUERY_TRANSACTION, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:BlockchainNumber
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> blockchainGetHeight(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_HEIGHT, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:Block
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> blockchainGetBlockByHash(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_BLOCK_BY_HASH, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainNumber, PeerResponse:Block
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> blockchainGetBlockByHeight(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_BLOCK_BY_HEIGHT, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:Transaction
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> blockchainGetTransaction(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_TRANSACTION, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:BlockchainNumber
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> blockchainGetTransactionIndex(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_TRANSACTION_INDEX, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: BlockchainHash, PeerResponse:BlockchainNumber
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> blockchainGetTransactionBlock(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_BLOCKCHAIN_GET_TRANSACTION_BLOCK, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:MemberList
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> getMemberList(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_GET_MEMBER_LIST, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerPermission, PeerResponse:PeerPermissionList
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> getPeerPermissions(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_GET_PEER_PERMISSIONS, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: SearchMes, PeerResponse:SearchRes
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> search(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_SEARCH, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerInfoAddr, PeerResponse:PeerNtx
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> getNtx(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_GET_NTX, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerInfoAddr, PeerResponse:PeerDetailInfo
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> getPeerInfo(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_GET_PEER_INFO, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:PeerStatusInfo
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> getPeerHealthData(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_GET_PEER_HEALTH_DATA, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: PeerRequest, PeerResponse:PeerVersion
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> getPeerVersion(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_GET_PEER_VERSION, getCallOptions()), request);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.Balance> getBalance(
                chain.tj.model.proto.MyPeer.BalanceAddress request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_GET_BALANCE, getCallOptions()), request);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.TotalRes> getTotal(
                chain.tj.model.proto.MyPeer.QueryTime request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_GET_TOTAL, getCallOptions()), request);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> saveFirstConn(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_SAVE_FIRST_CONN, getCallOptions()), request);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.OnChain> isOnChain(
                chain.tj.model.proto.MyPeer.TxId request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_IS_ON_CHAIN, getCallOptions()), request);
        }

        /**
         * <pre>
         * PeerRequest: subledger, PeerResponse:subledgers
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> ledgers(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_LEDGERS, getCallOptions()), request);
        }

        /**
         * <pre>
         * 通用数据接口 PeerRequest: GeneralReq, PeerResponse:GeneralResp
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<chain.tj.model.proto.MyPeer.PeerResponse> gDI(
                chain.tj.model.proto.MyPeer.PeerRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(METHOD_GDI, getCallOptions()), request);
        }
    }

    private static final int METHODID_NEW_TRANSACTION = 0;
    private static final int METHODID_NEW_QUERY_TRANSACTION = 1;
    private static final int METHODID_BLOCKCHAIN_GET_HEIGHT = 2;
    private static final int METHODID_BLOCKCHAIN_GET_BLOCK_BY_HASH = 3;
    private static final int METHODID_BLOCKCHAIN_GET_BLOCK_BY_HEIGHT = 4;
    private static final int METHODID_BLOCKCHAIN_GET_TRANSACTION = 5;
    private static final int METHODID_BLOCKCHAIN_GET_TRANSACTION_INDEX = 6;
    private static final int METHODID_BLOCKCHAIN_GET_TRANSACTION_BLOCK = 7;
    private static final int METHODID_GET_MEMBER_LIST = 8;
    private static final int METHODID_GET_PEER_PERMISSIONS = 9;
    private static final int METHODID_SEARCH = 10;
    private static final int METHODID_GET_NTX = 11;
    private static final int METHODID_GET_PEER_INFO = 12;
    private static final int METHODID_GET_PEER_HEALTH_DATA = 13;
    private static final int METHODID_GET_PEER_VERSION = 14;
    private static final int METHODID_GET_BALANCE = 15;
    private static final int METHODID_GET_TOTAL = 16;
    private static final int METHODID_SAVE_FIRST_CONN = 17;
    private static final int METHODID_IS_ON_CHAIN = 18;
    private static final int METHODID_LEDGERS = 19;
    private static final int METHODID_GDI = 20;

    private static class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final PeerImplBase serviceImpl;
        private final int methodId;

        public MethodHandlers(PeerImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_NEW_TRANSACTION:
                    serviceImpl.newTransaction((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_NEW_QUERY_TRANSACTION:
                    serviceImpl.newQueryTransaction((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_BLOCKCHAIN_GET_HEIGHT:
                    serviceImpl.blockchainGetHeight((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_BLOCKCHAIN_GET_BLOCK_BY_HASH:
                    serviceImpl.blockchainGetBlockByHash((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_BLOCKCHAIN_GET_BLOCK_BY_HEIGHT:
                    serviceImpl.blockchainGetBlockByHeight((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_BLOCKCHAIN_GET_TRANSACTION:
                    serviceImpl.blockchainGetTransaction((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_BLOCKCHAIN_GET_TRANSACTION_INDEX:
                    serviceImpl.blockchainGetTransactionIndex((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_BLOCKCHAIN_GET_TRANSACTION_BLOCK:
                    serviceImpl.blockchainGetTransactionBlock((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_GET_MEMBER_LIST:
                    serviceImpl.getMemberList((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_GET_PEER_PERMISSIONS:
                    serviceImpl.getPeerPermissions((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_SEARCH:
                    serviceImpl.search((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_GET_NTX:
                    serviceImpl.getNtx((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_GET_PEER_INFO:
                    serviceImpl.getPeerInfo((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_GET_PEER_HEALTH_DATA:
                    serviceImpl.getPeerHealthData((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_GET_PEER_VERSION:
                    serviceImpl.getPeerVersion((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_GET_BALANCE:
                    serviceImpl.getBalance((chain.tj.model.proto.MyPeer.BalanceAddress) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.Balance>) responseObserver);
                    break;
                case METHODID_GET_TOTAL:
                    serviceImpl.getTotal((chain.tj.model.proto.MyPeer.QueryTime) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.TotalRes>) responseObserver);
                    break;
                case METHODID_SAVE_FIRST_CONN:
                    serviceImpl.saveFirstConn((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_IS_ON_CHAIN:
                    serviceImpl.isOnChain((chain.tj.model.proto.MyPeer.TxId) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.OnChain>) responseObserver);
                    break;
                case METHODID_LEDGERS:
                    serviceImpl.ledgers((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                case METHODID_GDI:
                    serviceImpl.gDI((chain.tj.model.proto.MyPeer.PeerRequest) request,
                            (io.grpc.stub.StreamObserver<chain.tj.model.proto.MyPeer.PeerResponse>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
                io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        return new io.grpc.ServiceDescriptor(SERVICE_NAME,
                METHOD_NEW_TRANSACTION,
                METHOD_NEW_QUERY_TRANSACTION,
                METHOD_BLOCKCHAIN_GET_HEIGHT,
                METHOD_BLOCKCHAIN_GET_BLOCK_BY_HASH,
                METHOD_BLOCKCHAIN_GET_BLOCK_BY_HEIGHT,
                METHOD_BLOCKCHAIN_GET_TRANSACTION,
                METHOD_BLOCKCHAIN_GET_TRANSACTION_INDEX,
                METHOD_BLOCKCHAIN_GET_TRANSACTION_BLOCK,
                METHOD_GET_MEMBER_LIST,
                METHOD_GET_PEER_PERMISSIONS,
                METHOD_SEARCH,
                METHOD_GET_NTX,
                METHOD_GET_PEER_INFO,
                METHOD_GET_PEER_HEALTH_DATA,
                METHOD_GET_PEER_VERSION,
                METHOD_GET_BALANCE,
                METHOD_GET_TOTAL,
                METHOD_SAVE_FIRST_CONN,
                METHOD_IS_ON_CHAIN,
                METHOD_LEDGERS,
                METHOD_GDI);
    }

}
